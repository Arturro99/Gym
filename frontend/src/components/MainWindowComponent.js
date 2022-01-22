import {Component} from "react";
import {withTranslation} from "react-i18next";

class MainWindowComponent extends Component {

  componentDidMount() {
    this.props.changeImage('main');
  }

  render() {
    return (
      <div/>
    );
  }
}

export default withTranslation()(MainWindowComponent);