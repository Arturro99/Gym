import { Link, NavLink } from 'react-router-dom'
import React from "react";
import {
  getCurrentRole,
  getCurrentUser
} from "../../services/AuthenticationService";
import config from '../../config.json';
import { withTranslation } from "react-i18next";
import '../../styles.css'

const NavigationBar = (props) => {

  const { headers, data, onChangeRoleClick, onSignOutClick, t } = props;
  const mainHeaders = headers.filter(
      header => header.path !== '/login' && header.path !== '/register');
  const adminHeaders = mainHeaders.filter(
      header => header.path !== '/bookings' && header.path !== '/diets'
          && header.path !== '/activities' && header.path !== '/trainingPlans');
  const trainerHeaders = mainHeaders.filter(
      header => header.path !== '/accounts');
  const clientHeaders = trainerHeaders.filter(
      header => header.path !== '/bookings');
  const loginHeader = headers.filter(header => header.path === '/login');
  const registerHeader = headers.filter(header => header.path === '/register');

  const renderedHeaders = getCurrentRole() === config.ADMIN ? adminHeaders :
      getCurrentRole() === config.TRAINER ? trainerHeaders :
          getCurrentRole() === config.CLIENT ? clientHeaders : '';
  return (
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <Link className="navbar-brand logo ownOpacity" to="/"/>
        {getCurrentRole() ?
            <ul className="navbar-nav ms-sm-5">
              {renderedHeaders.map(header =>
                  <li className="nav-item mx-lg-5">
                    <NavLink to={header.path}
                             className="nav-link">{header.value}</NavLink>
                  </li>
              )}
            </ul> : ''}
        {getCurrentUser() === '' ?
            <ul className="navbar-nav ms-auto me-5">
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
                            to={`/accounts/own`}
                            type="button">{t('accountDetails')}</Link>
                      {getCurrentRole() === config.CLIENT ? <div>
                        <Link className="dropdown-item"
                              to={`/bookings/own`}
                              type="button">{t('myBookings')}</Link>
                        <Link className="dropdown-item"
                              to={`/trainingPlans/own`}
                              type="button">{t('myTrainingPlans')}</Link>
                        <Link className="dropdown-item"
                              to={`/diets/own`}
                              type="button">{t('myDiets')}</Link>
                      </div> : ''
                      }
                      <Link to={'/'} style={{ textDecoration: 'none' }}>
                        <button className="dropdown-item"
                                onClick={onSignOutClick}
                                type="button">{t('signOut')}</button>
                      </Link>
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