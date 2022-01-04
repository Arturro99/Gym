import Details from "../common/Details";
import { Account } from "../../model/Account";
import UpdateAccountForm from "./UpdateAccountModal";
import { parseFromOffsetDateTimeToLegibleFormat } from "../../services/DateParser";
import { getAccount } from "../../services/AccountService";
import { withTranslation } from "react-i18next";
import AccessLevelModal from "./AccessLevelModal";
import React from "react";
import { getAccessLevelsByAccount } from "../../services/AccessLevelService";

class AccountDetails extends Details {

  state = {
    data: {
      account: Account,
      roles: []
    }
  }

  async componentDidMount() {
    await this.updateDetails();
    await this.updateAccessLevels();

    const updateModal = document.getElementById('updateAccountModal')
    updateModal.addEventListener('hidden.bs.modal', () => {
      this.updateDetails();
    })

    const accessLevelModal = document.getElementById('accessLevelModal')
    accessLevelModal.addEventListener('hidden.bs.modal', () => {
      this.updateAccessLevels();
    })
  }

  async updateDetails() {
    const pathParam = this.props.match.params.login;
    const { t } = this.props;
    let currentState = { ...this.state };
    const fetched = await getAccount(pathParam);
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

  async updateAccessLevels() {
    const currentState = { ...this.state };
    currentState.data.roles = await getAccessLevelsByAccount(
        currentState.data.account.login).then(resp => {
      return resp.map(level => level.level);
    });
    this.setState ({ currentState });
  }

  render() {
    const { t } = this.props;
    const { account, roles } = this.state.data;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <UpdateAccountForm account={account}/>
          <AccessLevelModal account={account}
                            accessLevels={roles}/>

          <div className="card-header">
            <h1>{t('accountDetails')}</h1>
            {this.renderUpdateButton('updateAccountModal', t('update'))}
            <button className="btn btn-outline-warning float-end me-3"
                    data-bs-toggle='modal' data-bs-target='#accessLevelModal'>
              {t('accessLevels')}
            </button>
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
          {this.renderField('loyaltyFactor', t('loyaltyFactor'),
              account)}
          {this.renderField('gymMember', t('gymMember'),
              account)}
          {this.renderField('active', t('active'), account)}
          {this.renderField('confirmed', t('confirmed'),
              account)}
          {this.renderField('badLoginsCounter', t('badLoginsCounter'),
              account)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              account, true)}
          {this.renderField('creationDate', t('creationDate'),
              account)}
          {this.renderField('modificationDate', t('modificationDate'),
              account)}
        </div>
    );
  }
}

export default withTranslation()(AccountDetails);