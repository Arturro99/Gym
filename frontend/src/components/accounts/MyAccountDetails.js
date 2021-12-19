import '../../locales/i18n';
import { Account } from "../../model/Account";
import { withTranslation } from "react-i18next";
import Details from "../common/Details";
import UpdateAccountForm from "./UpdateAccountModal";

class MyAccountDetails extends Details {

  state = {
    data: {
      account: Account
    }
  }

  paginatedDiets = {};

  componentDidMount() {
    const pathParam = this.props.match.params.login;
    let currentState = { ...this.state };
    currentState.data.account.login = pathParam;
    currentState.data.account.firstName = 'Karol';
    currentState.data.account.lastName = 'AAA';
    currentState.data.account.email = 'Karol@karoleasdsadasdasddwski.mmm.pl';
    this.setState({ currentState });
    //TODO Populate details
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <UpdateAccountForm account={this.state.data.account}/>
          <div className="card-header">
            <h1>{t('myAccount')}</h1>
            {this.renderUpdateButton('updateAccountModal', t('update'))}
          </div>
          {this.renderField('login', t('login'), this.state.data.account)}
          {this.renderField('email', t('email'), this.state.data.account)}
          {this.renderField('firstName', t('firstName'),
              this.state.data.account)}
          {this.renderField('lastName', t('lastName'),
              this.state.data.account)}
          {this.renderField('phoneNumber', t('phoneNumber'),
              this.state.data.account)}
          {this.renderField('language', t('language'),
              this.state.data.account)}
          {this.renderField('loyaltyFactor', t('loyaltyFactor'),
              this.state.data.account)}
          {this.renderField('gymMember', t('gymMember'),
              this.state.data.account)}
          {this.renderField('active', t('active'), this.state.data.account)}
          {this.renderField('confirmed', t('confirmed'),
              this.state.data.account)}
        </div>
    );
  }
}

export default withTranslation()(MyAccountDetails);