import { withTranslation } from "react-i18next";
import { Component } from "react";

class ErrorComponent extends Component {
  render() {

    return (
        <div>
          <h1 className="text-center text-black-50 fst-italic">{this.props.t(this.props.title)}</h1>
          <h1 className="text-center text-black-50 fst-italic">{this.props.code}</h1>
        </div>
    )
  }
}

export default withTranslation()(ErrorComponent)