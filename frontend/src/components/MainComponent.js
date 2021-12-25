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
import MyTrainingPlansComponent from "./trainingPlans/MyTrainingPlansComponent";
import MyDietsComponent from "./diets/MyDietsComponent";
import AccountsComponent from "./accounts/AccountsComponent";
import TrainingPlanDetails from "./trainingPlans/TrainingPlanDetails";
import DietDetails from "./diets/DietDetails";
import BookingsComponent from "./bookings/BookingsComponent";
import MyBookingsComponent from "./bookings/MyBookingsComponent";
import ActivityDetails from "./activities/ActivityDetails";
import AccessLevelModal from "./accounts/AccessLevelModal";
import BookingDetails from "./bookings/BookingDetails";
import MyAccountComponent from "./accounts/MyAccountDetails";
import MyAccountDetails from "./accounts/MyAccountDetails";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'
import { Modal } from "bootstrap";
import {
  getCurrentRole,
  getCurrentUser, getRoles, signOut, switchCurrentRole
} from "../services/AuthenticationService";
//DO NOT REMOVE THIS -> MODAL WON'T WORK WITHOUT IT

class MainComponent extends Component {

  state = {
    headers: [
      { path: "/activities", value: `${this.props.t('activities')}` },
      { path: "/diets", value: `${this.props.t('diets')}` },
      { path: "/trainingPlans", value: `${this.props.t('trainingPlans')}` },
      { path: "/bookings", value: `${this.props.t('bookings')}` },
      { path: "/accounts", value: `${this.props.t('accounts')}` },
      { path: "/login", value: `${this.props.t('login')}` },
      { path: "/register", value: `${this.props.t('register')}` }
    ],
    data: {
      login: '',
      roles: [],
      currentRole: '',
    }
  }

  componentDidMount() {
    const myModalEl = document.getElementById('loginModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      console.log(getCurrentUser())
      if (getCurrentUser()) {
        this.resetData();
      }
    })
  }

  handleChangeRoleClick = role => {
    switchCurrentRole(role);
    this.resetData();
  }

  handleSignOutClick = () => {
    signOut();
    this.resetData();
  }

  resetData = () => {
    let currentData = { ...this.state.data };
    currentData.login = getCurrentUser()
    currentData.roles = getRoles()
    currentData.currentRole = getCurrentRole()
    this.setState({ data: currentData });
  }

  render() {
    const { headers, data } = this.state;
    return (
        <React.Fragment>
          <NavigationBar headers={headers}
                         data={data}
                         onChangeRoleClick={this.handleChangeRoleClick}
                         onSignOutClick={this.handleSignOutClick}/>
          <LoginForm resetData={this.resetData}/>
          <ToastContainer />
          <AccessLevelModal accessLevels={data.roles}/>
          <main className="container">
            <Switch>
              <Route exact path={'/activities'}
                     component={ActivitiesComponent}/>
              <Route exact path={'/diets'} component={DietsComponent}/>
              <Route exact path={'/trainingPlans'}
                     component={TrainingPlansComponent}/>
              <Route exact path={'/accounts'}
                     component={AccountsComponent}/>
              <Route exact path={'/accounts/own/:login'}
                     component={MyAccountDetails}/>
              <Route exact path={'/accounts/:login'}
                     render={(props) => <AccountDetails {...props}/>}/>
              <Route exact path={'/register'} component={RegisterForm}/>
              <Route path="/activities/new" component={ActivityForm}/>
              <Route path="/activities/:number"
                     render={(props) => <ActivityDetails {...props}/>}/>
              <Route path="/diets/new" component={DietForm}/>
              <Route path="/diets/own/:login"
                     render={(props) => <MyDietsComponent {...props}/>}/>
              <Route path="/diets/:number"
                     render={(props) => <DietDetails {...props}/>}/>
              <Route path="/trainingPlans/own/:login"
                     render={(props) =>
                         <MyTrainingPlansComponent {...props}/>}/>
              <Route path="/trainingPlans/new" component={TrainingPlanForm}/>
              <Route path="/trainingPlans/:number"
                     render={(props) => <TrainingPlanDetails {...props}/>}/>
              <Route exact path={'/bookings'}
                     component={BookingsComponent}/>
              <Route path="/bookings/own/:login"
                     render={(props) => <MyBookingsComponent {...props}/>}/>
              <Route path="/bookings/:number"
                     render={(props) => <BookingDetails {...props}/>}/>
            </Switch>
          </main>
        </React.Fragment>
    );
  }
}

export default withTranslation()(MainComponent);