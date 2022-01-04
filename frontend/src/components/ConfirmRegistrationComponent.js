import { Component } from "react";
import { confirmRegistration } from "../services/AuthenticationService";
import { withTranslation } from "react-i18next";

class ConfirmRegistrationComponent extends Component {

  async componentDidMount() {
    const token = window.location.pathname.split('/')[2];
    await confirmRegistration(token, this.props.t);
  }

  render() {
    return (
        <div/>
    );
  }
}

export default withTranslation()(ConfirmRegistrationComponent);