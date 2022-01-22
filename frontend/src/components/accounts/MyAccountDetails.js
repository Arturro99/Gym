import '../../locales/i18n';
import { Account } from "../../model/Account";
import { withTranslation } from "react-i18next";
import Details from "../common/Details";
import UpdateAccountForm from "./UpdateAccountModal";
import { getCurrentRole } from "../../services/AuthenticationService";
import config from "../../config.json";
import { getOwnAccount } from "../../services/AccountService";
import { parseFromOffsetDateTimeToLegibleFormat } from "../../services/DateParser";

class MyAccountDetails extends Details {

  state = {
    data: {
      account: Account
    }
  }

  paginatedDiets = {};

  componentDidMount() {
    this.props.changeImage('accountDetails');
    this.updateDetails();
    const myModalEl = document.getElementById('updateAccountModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      this.updateDetails();
    })
  }

  updateDetails = async () => {
    const { t } = this.props;
    let currentState = { ...this.state };
    const fetched = await getOwnAccount();
    currentState.data.account = new Account(
        fetched.login,
        fetched.email,
        fetched.active ? t('active') : t('inactive'),
        fetched.confirmed ? t('confirmed') : t('unconfirmed'),
        fetched.firstName,
        fetched.lastName,
        fetched.phoneNumber,
        fetched.language,
        fetched.loyaltyFactor,
        fetched.gymMember ? t('yes') : t('no'),
        fetched.badLoginsCounter,
        fetched.modifiedBy,
        parseFromOffsetDateTimeToLegibleFormat(fetched.creationDate),
        fetched.modificationDate ? parseFromOffsetDateTimeToLegibleFormat(
            fetched.modificationDate) : ''
    );
    this.setState({ currentState });
  }

  render() {
    const { t } = this.props;
    const { account } = this.state.data;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto bg-light">
          <UpdateAccountForm account={this.state.data.account}
                             own={true}/>
          <div className="card-header">
            <h1>{t('myAccount')}</h1>
            {this.renderRefreshButton(this.updateDetails, t)}
            {this.renderUpdateButton('updateAccountModal', t('update'))}
          </div>
          {this.renderField('login', t('login'), account)}
          {this.renderField('email', t('email'), account)}
          {this.renderField('firstName', t('firstName'),
              account)}
          {this.renderField('lastName', t('lastName'),
              account)}
          {this.renderField('phoneNumber', t('phoneNumber'),
              account)}
          {this.renderField('language', t('language'),
              account)}

          {getCurrentRole() === config.CLIENT ?
              this.renderField('loyaltyFactor', t('loyaltyFactor'),
                  account) : ''}
          {getCurrentRole() === config.CLIENT ?
              this.renderField('gymMember', t('gymMember'),
                  account) : ''}

          {getCurrentRole() === config.ADMIN ?
              this.renderField('active', t('active'),
                  account) : ''}
          {getCurrentRole() === config.ADMIN ?
              this.renderField('confirmed', t('confirmed'),
                  account) : ''}
          {getCurrentRole() === config.ADMIN ?
              this.renderField('badLoginsCounter', t('badLoginsCounter'),
                  account) : ''}
          {getCurrentRole() === config.ADMIN ?
              this.renderField('modifiedBy', t('modifiedBy'),
                  account) : ''}
          {getCurrentRole() === config.ADMIN ?
              this.renderField('creationDate', t('creationDate'),
                  account) : ''}
          {getCurrentRole() === config.ADMIN ?
              this.renderField('modificationDate', t('modificationDate'),
                  account) : ''}
        </div>
    );
  }
}

export default withTranslation()(MyAccountDetails);