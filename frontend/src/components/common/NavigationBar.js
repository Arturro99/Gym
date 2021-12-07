import { Link, NavLink } from 'react-router-dom'

const NavigationBar = (props) => {

  const { headers } = props;
  const mainHeaders = headers.filter(
      header => header.path !== '/login' && header.path !== '/register');
  const otherHeaders = headers.filter(header => !mainHeaders.includes(header));

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
          {otherHeaders.map(header =>
              <li className="nav-item mx-lg-2">
                <NavLink to={header.path}
                         className="nav-link">{header.value}</NavLink>
              </li>
          )}
        </ul>
      </nav>
  )
}

export default NavigationBar;