import { withTranslation } from "react-i18next";
import { Component } from "react";

class LoginForm extends Component {

  state = {
    data: {}
  }

  handleChange = ({ currentTarget: input }) => {
    const data = this.state.data;
    console.log(input)
    data[input.name] = input.value;
    this.setState({ data });
    console.log(this.state)
  }

  handleLogin = event => {
    event.preventDefault();
    console.log(`User: ${this.state.data.login} logged in`)
  }

  render() {
    const { t } = this.props;
    return (
        <div className="modal fade" id="loginModal" tabIndex="-1"
             role="dialog" aria-labelledby="exampleModalCenterTitle"
             aria-hidden="true">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content bg-secondary">
              <div className="modal-header text-light">
                <h1 className="modal-title position-relative start-50 translate-middle-x">
                  {t('signIn')}</h1>
                <button type="button" className="btn-close btn-light"
                        data-bs-dismiss="modal" aria-label="Close"/>
              </div>
              <div className="modal-body">
                <label className="login-label">{t('login')}</label>
                <input type="text" name="login" value={this.state.data.login}
                       onChange={this.handleChange}
                       className="login-box position-relative start-50 translate-middle-x text-light"/>
                <label className="login-label">{t('password')}</label>
                <input type="text" name="password"
                       value={this.state.data.password}
                       onChange={this.handleChange}
                       className="login-box position-relative start-50 translate-middle-x text-light"/>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary"
                        onClick={this.handleLogin}>
                  {t('signIn')}</button>
                <button type="button" className="btn btn-secondary"
                        data-bs-toggle='modal'
                        data-bs-target='#loginModal'>
                  {t('cancel')}</button>
              </div>
            </div>
          </div>
        </div>
    );
  }

}

export default withTranslation()(LoginForm);