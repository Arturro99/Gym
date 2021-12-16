import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { Account } from "../../model/Account";

class AccountDetails extends Details {

  state = {
    data: {
      account: Account
    }
  }

  componentDidMount() {
    const pathParam = this.props.match.params.login;
    let currentState = { ...this.state };
    currentState.data.account.login = pathParam;
    currentState.data.account.firstName = 'Karol';
    currentState.data.account.email = 'Karol@karoleasdsadasdasddwski.mmm.pl';
    this.setState({ currentState });
    console.log('DONE')
    //TODO Populate details
  }

  render() {
    const { t } = this.props;
    //TODO render some fields depending on current access level
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <div className="card-header">
            <h1>{t('accountDetails')}</h1>
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
          {this.renderField('lastKnownGoodLogin', t('lastKnownGoodLogin'),
              this.state.data.account)}
          {this.renderField('lastKnownGoodLoginIp', t('lastKnownGoodLoginIp'),
              this.state.data.account)}
          {this.renderField('lastKnownBadLogin', t('lastKnownBadLogin'),
              this.state.data.account)}
          {this.renderField('lastKnownBadLoginIp', t('lastKnownBadLoginIp'),
              this.state.data.account)}
          {this.renderField('badLoginsCounter', t('badLoginsCounter'),
              this.state.data.account)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.account)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.account)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.account)}
        </div>
    );
  }
}

export default withTranslation()(AccountDetails);