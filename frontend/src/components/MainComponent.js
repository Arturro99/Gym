import React, {Component} from "react";
import {Redirect, Route, Switch} from 'react-router'
import '../locales/i18n';
import ActivitiesComponent from "./activities/ActivitiesComponent";
import TrainingPlansComponent from "./trainingPlans/TrainingPlansComponent";
import RegisterForm from "./accounts/RegisterForm";
import LoginForm from "./LoginForm";
import NavigationBar from "./common/NavigationBar";
import ActivityForm from "./activities/ActivityForm";
import {withTranslation} from "react-i18next";
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
import BookingDetails from "./bookings/BookingDetails";
import MyAccountDetails from "./accounts/MyAccountDetails";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'
import {getCurrentRole, getCurrentUser, getRoles, signOut, switchCurrentRole} from "../services/AuthenticationService";
import ConfirmRegistrationComponent from "./ConfirmRegistrationComponent";
import MainWindowComponent from "./MainWindowComponent";
import ErrorComponent from "./errors/ErrorComponent";
import DietsComponent from "./diets/DietsComponent";

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
    },
    obj: ''
  }

  componentDidMount() {
    this.resetData();
    const myModalEl = document.getElementById('loginModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      if (getCurrentUser()) {
        this.resetData();
      }
    })
  }

  changeImage = img => {
    this.setState({ obj: img })
    this.forceUpdate();
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
      <div className={this.state.obj} style={{ height: 1000 }}>
        <React.Fragment>
          <NavigationBar headers={headers}
                         data={data}
                         onChangeRoleClick={this.handleChangeRoleClick}
                         onSignOutClick={this.handleSignOutClick}/>
          <LoginForm resetData={this.resetData}/>
          <ToastContainer/>
          <main className="container">
            <Switch>
              <Route exact path={'/activities'}
                     render={() => <ActivitiesComponent changeImage={this.changeImage}/>}/>
              <Route exact path={'/diets'}
                     render={() => <DietsComponent changeImage={this.changeImage}/>}/>/>
              <Route exact path={'/trainingPlans'}
                     render={() => <TrainingPlansComponent changeImage={this.changeImage}/>}/>/>
              <Route exact path={'/accounts'}
                     render={() => <AccountsComponent changeImage={this.changeImage}/>}/>/>
              <Route exact path={'/accounts/own'}
                     render={() => <MyAccountDetails changeImage={this.changeImage}/>}/>/>
              <Route exact path={'/accounts/:login'}
                     render={(props) => <AccountDetails {...props} changeImage={this.changeImage}/>}/>
              <Route exact path={'/register'} component={RegisterForm}/>
              <Route path="/activities/new" component={ActivityForm}/>
              <Route path="/activities/:number"
                     render={(props) => <ActivityDetails {...props}/>}/>
              <Route path="/diets/new" component={DietForm}/>
              <Route exact path="/diets/own"
                     render={() => <MyDietsComponent changeImage={this.changeImage}/>}/>/>
              <Route path="/diets/:number"
                     render={(props) => <DietDetails {...props}/>}/>
              <Route exact path="/trainingPlans/own"
                     render={() => <MyTrainingPlansComponent changeImage={this.changeImage}/>}/>/>
              <Route path="/trainingPlans/new" component={TrainingPlanForm}/>
              <Route path="/trainingPlans/:number"
                     render={(props) => <TrainingPlanDetails {...props}/>}/>
              <Route exact path={'/bookings'}
                     render={() => <BookingsComponent changeImage={this.changeImage}/>}/>
              <Route exact path="/bookings/own"
                     render={() => <MyBookingsComponent changeImage={this.changeImage}/>}/>
              <Route exact path="/bookings/own/:number"
                     render={(props) => <BookingDetails {...props}
                                                        own={true}/>}/>
              <Route exact path="/bookings/:number"
                     render={(props) => <BookingDetails {...props}/>}/>
              <Route exact path={'/confirmRegistration/:token'}
                     render={(props) =>
                       <ConfirmRegistrationComponent {...props} />}/>
              <Route exact path={'/'}
                     render={() => <MainWindowComponent changeImage={this.changeImage}/>}/>/>
              <Route exact path={'/error403'}
                     render={() =>
                       <ErrorComponent title="access_forbidden"
                                       code="403"/>}/>
              <Route exact path='/error404'
                     render={() =>
                       <ErrorComponent title="not_found"
                                       code="404"/>}/>
              <Redirect from='*' to='/error404'/>
            </Switch>
          </main>
        </React.Fragment>
      </div>
    );
  }
}

export default withTranslation()(MainComponent);