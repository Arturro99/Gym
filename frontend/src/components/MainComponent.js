import NavigationBar from "./common/NavigationBar";
import React, { Component } from "react";
import { Route, Switch } from 'react-router'
import '../locales/i18n';
import ActivitiesComponent from "./ActivitiesComponent";
import DietsComponent from "./DietsComponent";
import TrainingPlansComponent from "./TrainingPlansComponent";
import RegisterForm from "./RegisterForm";

export default class MainComponent extends Component {

  state = {
    headers: [
      { path: "/activities", value: "Activity" },
      { path: "/diets", value: "Diets" },
      { path: "/trainingPlans", value: "Training plans" },
      { path: "/login", value: "Login" },
      { path: "/register", value: "Register" }
    ]
  }

  render() {
    const { headers } = this.state;
    return (
        <React.Fragment>
          <NavigationBar headers={this.state.headers}/>
          <main className="container">
            <Switch>
              <Route exact path={headers[0].path}
                     component={ActivitiesComponent}/>
              <Route exact path={headers[1].path} component={DietsComponent}/>
              <Route exact path={headers[2].path}
                     component={TrainingPlansComponent}/>
              <Route exact path={headers[4].path} component={RegisterForm}/>
            </Switch>
          </main>
        </React.Fragment>
    );
  }
}

// export default withTranslation()(MainComponent);