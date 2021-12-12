import React, { Component } from "react";
import { Route, Switch } from 'react-router'
import '../locales/i18n';
import ActivitiesComponent from "./activities/ActivitiesComponent";
import DietsComponent from "./diets/DietsComponent";
import TrainingPlansComponent from "./trainingPlans/TrainingPlansComponent";
import RegisterForm from "./accounts/RegisterForm";
import LoginForm from "./LoginForm";
import NavigationBar from "./common/NavigationBar";
import ActivityForm from "./activities/ActivityForm";
import { withTranslation } from "react-i18next";
import DietForm from "./diets/DietForm";
import TrainingPlanForm from "./trainingPlans/TrainingPlanForm";
import AccountDetails from "./accounts/AccountDetails";
import { Modal } from "bootstrap";
import MyTrainingPlansComponent from "./trainingPlans/MyTrainingPlansComponent";
import MyDietsComponent from "./diets/MyDietsComponent";
//DO NOT REMOVE THIS -> MODAL WON'T WORK WITHOUT IT

class MainComponent extends Component {

  state = {
    headers: [
      { path: "/activities", value: `${this.props.t('activities')}` },
      { path: "/diets", value: `${this.props.t('diets')}` },
      { path: "/trainingPlans", value: `${this.props.t('trainingPlans')}` },
      { path: "/accounts", value: `${this.props.t('accounts')}` },
      { path: "/login", value: `${this.props.t('login')}` },
      { path: "/register", value: `${this.props.t('register')}` }
    ],
    data: {
      login: 'halo',
      roles: ['trainer', 'client', 'admin'],
      currentRole: 'admin'
    }
  }

  handleChangeRoleClick = role => {
    let newData = { ...this.state.data };
    newData.currentRole = role;
    this.setState({ data: newData });
    console.log(`Switched to role: ${role}`);
  }

  handleSignOutClick = () => {
    console.log(`User: ${this.state.data.login} signed out.`)
  }

  render() {
    const { headers, data } = this.state;
    return (
        <React.Fragment>
          <NavigationBar headers={headers}
                         data={data}
                         onChangeRoleClick={this.handleChangeRoleClick}
                         onSignOutClick={this.handleSignOutClick}/>
          <LoginForm/>
          <main className="container">
            <Switch>
              <Route exact path={headers[0].path}
                     component={ActivitiesComponent}/>
              <Route exact path={headers[1].path} component={DietsComponent}/>
              <Route exact path={headers[2].path}
                     component={TrainingPlansComponent}/>
              <Route exact path={headers[3].path + "/:login"}
                     render={(props) => <AccountDetails {...props}/>}/>
              <Route exact path={headers[5].path} component={RegisterForm}/>
              <Route path="/activities/new" component={ActivityForm}/>
              <Route path="/diets/new" component={DietForm}/>
              <Route path="/diets/:login"
                     render={(props) => <MyDietsComponent {...props}/>}/>
              <Route path="/trainingPlans/:login"
                     render={(props) => <MyTrainingPlansComponent {...props}/>}/>
              <Route path="/trainingPlans/new" component={TrainingPlanForm}/>
            </Switch>
          </main>
        </React.Fragment>
    );
  }
}

export default withTranslation()(MainComponent);