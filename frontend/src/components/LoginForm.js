import { withTranslation } from "react-i18next";
import React from "react";
import Joi from "joi";
import Form from "./common/Form";

class LoginForm extends Form {

  state = {
    data: {
      login: '',
      password: ''
    },
    errors: {}
  }

  fieldRestrictions = {
    login: Joi.string().alphanum().required(),
    password: Joi.string().required()
  };

  schema = Joi.object({
    login: this.fieldRestrictions.login,
    password: this.fieldRestrictions.password
  });

  handleChange = ({ currentTarget: input }) => {
    const data = this.state.data;
    data[input.name] = input.value;
    this.setState({ data });
  }

  continueSubmitting = () => {
    console.log(`User: ${this.state.data.login} logged in`)
  }

  componentDidMount() {
    const myModalEl = document.getElementById('loginModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      let currentData = { ...this.state.data };
      currentData.login = '';
      currentData.password = '';
      this.setState({ data: currentData });
    })
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
                {this.renderInput('login', t('login'), 'text',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light')}
                {this.renderInput('password', t('password'), 'password',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light')}
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary"
                        onClick={this.handleSubmit}
                        disabled={this.validate()}
                        data-bs-toggle='modal'
                        data-bs-target='#loginModal'>
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