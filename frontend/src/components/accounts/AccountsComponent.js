import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Component } from "react";
import AccountsTable from "./AccountsTable";
import { Account } from "../../model/Account";

class AccountsComponent extends Component {

  state = {
    accounts: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'login', order: 'asc' }
  }

  paginatedAccounts = {};

  componentDidMount() {
    const acc1 = new Account();
    acc1.login = 'acc1';
    acc1.email = 'acc1@aaa.pl';
    acc1.active = 'Active';
    acc1.confirmed = 'confirmed';
    acc1.firstName = 'Karol';
    acc1.lastName = 'Karolewski';
    acc1.phoneNumber = '123456987';
    const acc2 = new Account();
    acc2.login = 'acc2';
    acc2.email = 'acc2@aaa.pl';
    acc2.active = 'Inactive';
    acc2.confirmed = 'unconfirmed';
    acc2.firstName = 'Jan';
    acc2.lastName = 'Janowicz';
    acc2.phoneNumber = '987456321';
    this.setState({
      accounts: [acc1, acc2]
    })

    //TODO fetch accounts and map active/confirmed from true/false values to strings
  }

  handleActivate = account => {
    const accounts = this.state.accounts;
    const index = accounts.findIndex(acc => acc.login === account.login);
    accounts[index].active = (accounts[index].active === this.props.t('active'))
        ? this.props.t('inactive')
        : this.props.t('active');
    this.setState({ accounts: accounts });

    //TODO implement activation/deactivation
  }

  handleAccessLevels = account => {
    //TODO add modal with pointers for access levels
  }

  handleUpdate = account => {
    //TODO implement account update
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
                           onSort={this.handleSort}/>
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