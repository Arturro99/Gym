import React, { Component } from "react";
import Input from "./Input";
import '../../locales/i18n'

export default class Form extends Component {

  state = {
    data: {},
    errors: {}
  }

  handleChange = ({ currentTarget: input }) => {

  }

  validate = () => {
    const options = { abortEarly: true };
    const { error } = this.schema.validate(this.state.data, options);

    if (!error) {
      return null;
    }

    const errors = {};
    error.details.forEach(item => {
      errors[item.path[0]] = item.message;
    });
    console.log(error);
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
        <button className="btn btn-primary"
                disabled={this.validate()}>label</button>
    )
  }

  renderInput(name, label, type = 'text') {
    const { data, errors } = this.state;
    return (
        <Input name={name}
               label={label}
               value={data[name]}
               type={type}
               onChange={this.handleChange}
               error={errors[name]}/>
    )
  }
}