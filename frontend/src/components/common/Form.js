import React, { Component } from "react";
import Input from "./Input";
import '../../locales/i18n'
import Joi from "joi";

export default class Form extends Component {

  constructor(props) {
    super(props);
  }

  state = {
    data: {},
    errors: {}
  }

  handleChange = ({ currentTarget: input }) => {
    const errors = this.state.errors;
    const error = this.validateProperty(input);
    if (error) {
      errors[input.name] = error[input.name].msg;
    } else {
      delete errors[input.name];
    }

    const data = this.state.data;
    data[input.name] = input.value;

    this.setState({ data, errors });
  }

  validateProperty = ({ name, value }) => {
    let obj = { [name]: value };
    const schema = Joi.object({ [name]: this.fieldRestrictions[name] });
    const { error } = schema.validate(obj);

    return error ? this.bindErrorMessageToOwnMessage(error.details) : null;
  }

  bindErrorMessageToOwnMessage = (details) => {
    const ownErrors = {};

    details.map(error => {
      if (!ownErrors.hasOwnProperty(error.path.join('_'))) {
        ownErrors[error.path.join('_')] = {
          type: error.type,
          msg: `error_${error.path.join('_')}_${error.type}`.replaceAll('.',
              '_')
        };
      }
    });
    return ownErrors;
  }

  validate = () => {
    const options = { abortEarly: false };
    const { error } = this.schema.validate(this.state.data, options);
    if (!error) {
      return null;
    }

    const errors = {};
    error.details.forEach(item => {
      errors[item.path[0]] = item.message;
    });
    return errors;
  }

  handleSubmit = event => {
    event.preventDefault();

    const errors = this.validate();
    this.setState({ errors: errors || {} });
    if (errors) {
      return;
    }
    this.continueSubmitting();
  }

  renderButton(label) {
    return (
        <button className="btn btn-primary float-end mt-3"
                disabled={this.validate()}>{label}</button>
    )
  }

  renderInput(name, label, type = 'text', labelClassName, inputClassName) {
    const { data, errors } = this.state;
    const { t } = this.props;
    return (
        <Input name={name}
               label={label}
               value={data[name]}
               type={type}
               onChange={this.handleChange}
               error={t(errors[name])}
               labelClassName={labelClassName}
               inputClassName={inputClassName}/>
    )
  }
}