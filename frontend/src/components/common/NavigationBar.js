import { Link, NavLink } from 'react-router-dom'
import React from "react";
import { withTranslation } from "react-i18next";

const NavigationBar = (props) => {

  const { headers, data, onChangeRoleClick, onSignOutClick, t } = props;
  const mainHeaders = headers.filter(
      header => header.path !== '/login' && header.path !== '/register');
  const loginHeader = headers.filter(header => header.path === '/login');
  const registerHeader = headers.filter(header => header.path === '/register');

  return (
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <Link className="navbar-brand" to="/">GymManiac</Link>
        <ul className="navbar-nav ms-sm-5">
          {mainHeaders.map(header =>
              <li className="nav-item mx-lg-5">
                <NavLink to={header.path}
                         className="nav-link">{header.value}</NavLink>
              </li>
          )}
        </ul>
        {data.login === '' ?
            <ul className="navbar-nav ms-auto">
              {registerHeader.map(header =>
                  <li className="nav-item mx-lg-2">
                    <NavLink to={header.path}
                             className="nav-link">{header.value}</NavLink>
                  </li>
              )}
              {loginHeader.map(header =>
                  <li className="nav-item mx-lg-2">
                    <button className="btn btn-primary" data-bs-toggle='modal'
                            data-bs-target='#loginModal'>{header.value}</button>
                  </li>
              )}
            </ul> :
            <ul className="navbar-nav ms-auto me-5 fs-5">
              <div className="collapse navbar-collapse fs-5 me-auto"
                   id="navbarNavDarkDropdown">
                <ul className="navbar-nav">
                  <li className="nav-item dropdown">
                    <li className="nav-link dropdown-toggle"
                       id="navbarDarkDropdownMenuLink" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                      {data.currentRole}
                    </li>
                    <ul className="dropdown-menu dropdown-menu-dark dropdown-menu-end text"
                        aria-labelledby="navbarDarkDropdownMenuLink">
                      {data.roles.map(role =>
                          <button className="dropdown-item"
                                  onClick={() => onChangeRoleClick(role)}
                                  type="button">{role}</button>
                      )}
                    </ul>
                  </li>
                </ul>
              </div>
              <div className="collapse navbar-collapse me-5 ms-3"
                   id="navbarNavDarkDropdown">
                <ul className="navbar-nav">
                  <li className="nav-item dropdown">
                    <li className="nav-link dropdown-toggle"
                        id="navbarDarkDropdownMenuLink" role="button"
                        data-bs-toggle="dropdown" aria-expanded="false">
                      {data.login}
                    </li>
                    <ul className="dropdown-menu dropdown-menu-dark dropdown-menu-end text"
                        aria-labelledby="navbarDarkDropdownMenuLink">
                      <Link className="dropdown-item"
                            to={`/accounts/${data.login}`}
                            type="button">{t('accountDetails')}</Link>
                      <Link className="dropdown-item"
                            to={`/bookings/own/${data.login}`}
                            type="button">{t('myBookings')}</Link>
                      <Link className="dropdown-item"
                            to={`/trainingPlans/own/${data.login}`}
                            type="button">{t('myTrainingPlans')}</Link>
                      <Link className="dropdown-item"
                            to={`/diets/own/${data.login}`}
                            type="button">{t('myDiets')}</Link>
                      <button className="dropdown-item"
                              onClick={onSignOutClick}
                              type="button">{t('signOut')}</button>
                    </ul>
                  </li>
                </ul>
              </div>
            </ul>
        }
      </nav>
  )
}

export default withTranslation()(NavigationBar);