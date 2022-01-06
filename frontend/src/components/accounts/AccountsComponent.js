import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import React, { Component } from "react";
import AccountsTable from "./AccountsTable";
import {
  blockAccount,
  getAccounts,
  unblockAccount
} from "../../services/AccountService";

class AccountsComponent extends Component {

  state = {
    accounts: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'login', order: 'asc' }
  }

  paginatedAccounts = {};

  async componentDidMount() {
    const { t } = this.props;
    await this.resetAccounts(t);
  }

  handleActivate = async account => {
    const { t } = this.props
    if (account.active === t('active')) {
      await blockAccount(account.login, t)
      .catch(() => this.resetAccounts())
      .then(() => this.resetAccounts());
    } else {
      await unblockAccount(account.login, t)
      .then(() => this.resetAccounts());
    }
  }

  resetAccounts = async () => {
    const { t } = this.props;
    const accounts = await getAccounts();
    accounts.forEach(account => {
      account.active = account.active ? t('active') : t('inactive');
      account.confirmed = account.confirmed ? t('confirmed') : t('unconfirmed');
    })
    this.setState({
      accounts: accounts
    });
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const { length: count } = this.state.accounts;
    const {
      pageSize,
      currentPage,
      sortColumn,
      accounts
    } = this.state;
    const { t } = this.props;

    if (count === 0) {
      return <h1>{t('noAccounts')}</h1>;
    }

    return (
        <div className="row mt-5">
          <div className="col">
            {/*<Search onSearchChange={this.handleSearchChange}*/}
            {/*        value={this.state.searchQuery}/>*/}
            <AccountsTable accounts={accounts}
                           sortColumn={sortColumn}
                           onActivate={this.handleActivate}
                           onAccessLevels={this.handleAccessLevels}
                           onUpdate={this.handleUpdate}
                           onSort={this.handleSort}
                           onRefresh={this.resetAccounts}/>
            {/*<Pagination*/}
            {/*    itemsCount={totalCount}*/}
            {/*    pageSize={pageSize}*/}
            {/*    onPageChange={this.handlePageChange}*/}
            {/*    currentPage={currentPage}/>*/}
          </div>
        </div>
    );
  }

}

export default withTranslation()(AccountsComponent);