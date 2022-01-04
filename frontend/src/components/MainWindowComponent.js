import { Component } from "react";
import { confirmRegistration } from "../services/AuthenticationService";
import { withTranslation } from "react-i18next";

class MainWindowComponent extends Component {

  render() {
    return (
        <div/>
    );
  }
}

export default withTranslation()(MainWindowComponent);