import { Link, NavLink } from 'react-router-dom'

const NavigationBar = (props) => {

  const { headers } = props;
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
        </ul>
      </nav>
  )
}

export default NavigationBar;