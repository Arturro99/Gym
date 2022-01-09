import Joi from "joi";
import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import { toast } from "react-toastify";
import { register } from "../../services/AccountService";
import { Account } from "../../model/Account";

class RegisterForm extends Form {

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
    phoneNumber: Joi.string().min(9).max(12).required().allow("")
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

  continueSubmitting = async () => {
    const { t } = this.props;
    const {
      login,
      email,
      password,
      firstName,
      lastName,
      phoneNumber
    } = this.state.data;
    if (this.state.data.password !== this.state.data.repeatedPassword) {
      toast.error(t('incorrect_repeated_password'))
      return
    }

    let account = new Account();
    account.login = login;
    account.email = email;
    account.password = password;
    account.firstName = firstName;
    account.lastName = lastName;
    account.phoneNumber = phoneNumber;
    await register(account, t).then(resp => {
      if (resp && resp.status === 200) {
        this.props.history.push("/");
      }
    });
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card-header mt-5 w-50 mx-auto">
          <h1 className="text-center">Register</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("login", t("login"))}
            {this.renderInput("email", t("email"), "email")}
            {this.renderInput("password", t("password"), "password")}
            {this.renderInput("repeatedPassword", t("repeatedPassword"),
                "password")}
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