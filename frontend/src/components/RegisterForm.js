import Joi from "joi";
import Form from "./common/Form";
import { withTranslation } from "react-i18next";

class RegisterForm extends Form {

  state = {
    data: {
      login: '',
      email: '',
      password: '',
      firstName: '',
      lastName: '',
      phoneNumber: '',
    },
    errors: {}
  }

  fieldRestrictions = {
    login: Joi.string().alphanum().required(),
    email: Joi.string().email({ tlds: false }).required(),
    password: Joi.string().pattern(
        /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$/),
    firstName: Joi.string().required(),
    lastName: Joi.string().required(),
    phoneNumber: Joi.string().min(9).max(11).pattern(/^[0-9]+$/).required(),
  };

  schema = Joi.object({
    login: this.fieldRestrictions.login,
    email: this.fieldRestrictions.email,
    password: this.fieldRestrictions.password,
    firstName: this.fieldRestrictions.firstName,
    lastName: this.fieldRestrictions.lastName,
    phoneNumber: this.fieldRestrictions.phoneNumber,
  });

  continueSubmitting = () => {
    console.log("Registered user: {}", this.state.data);
  }

  render() {
    const { t } = this.props;
    return (
        <div>
          <h1>Register</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("login", t("login"))}
            {this.renderInput("email", t("email"), "email")}
            {this.renderInput("password", t("password"), "password")}
            {this.renderInput("firstName", t("firstName"))}
            {this.renderInput("lastName", t("lastName"))}
            {this.renderInput("phoneNumber", t("phoneNumber"), "number")}
          </form>
        </div>
    );
  }
}

export default withTranslation()(RegisterForm);