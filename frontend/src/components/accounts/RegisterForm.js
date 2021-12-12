import Joi from "joi";
import Form from "../common/Form";
import { withTranslation } from "react-i18next";

class RegisterForm extends Form {

  constructor(props) {
    super(props);
  }

  state = {
    data: {
      login: '',
      email: '',
      password: '',
      repeatedPassword: '',
      firstName: '',
      lastName: '',
      phoneNumber: '',
    },
    errors: {}
  }

  fieldRestrictions = {
    login: Joi.string().alphanum().required().messages({
      'string.alphanum': ''
    }),
    email: Joi.string().email({ tlds: false }).required(),
    password: Joi.string().pattern(
        /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$/),
    repeatedPassword: Joi.string().required(),
    firstName: Joi.string().required(),
    lastName: Joi.string().required(),
    phoneNumber: Joi.string().min(9).max(11).optional().allow("")
  };

  schema = Joi.object({
    login: this.fieldRestrictions.login,
    email: this.fieldRestrictions.email,
    password: this.fieldRestrictions.password,
    repeatedPassword: this.fieldRestrictions.repeatedPassword,
    firstName: this.fieldRestrictions.firstName,
    lastName: this.fieldRestrictions.lastName,
    phoneNumber: this.fieldRestrictions.phoneNumber,
  });

  continueSubmitting = () => {
    if (this.state.data.password !== this.state.data.repeatedPassword) {
      alert("DIPA")
      return
    }
    console.log("Registered user: {}", this.state.data);
  }

  render() {
    const { t } = this.props;
    return (
        <div>
          <h1 className="modal-header">Register</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("login", t("login"))}
            {this.renderInput("email", t("email"), "email")}
            {this.renderInput("password", t("password"), "password")}
            {this.renderInput("repeatedPassword", t("repeatedPassword"), "password")}
            {this.renderInput("firstName", t("firstName"))}
            {this.renderInput("lastName", t("lastName"))}
            {this.renderInput("phoneNumber", t("phoneNumber"), "number")}
            {this.renderButton(t("register"))}
          </form>
        </div>
    );
  }
}

export default withTranslation()(RegisterForm);