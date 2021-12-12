import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Component } from "react";
import { Activity } from "../../model/Activity";
import { Link } from "react-router-dom";
import AccountsTable from "./AccountsTable";

class AccountsComponent extends Component {

  state = {
    accounts: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'login', order: 'asc' }
  }

  paginatedAccounts = {};

  componentDidMount() {
    const act1 = new Activity(
        'ACT001',
        'Rozciaganie',
        '12.12.2022',
        60,
        'trener',
        'true',
        '2'
    )
    const act2 = new Activity(
        'ACT002',
        'Bieganie',
        '12.03.2022',
        90,
        'trener',
        'true',
        '5'
    )
    this.setState({
      accounts: [act1, act2]
    })
  }

  handleDelete = activity => {
    const originalAccounts = this.state.accounts;
    const currentAccounts = originalAccounts.filter(
        act => act.number !== activity.number);
    this.setState({ accounts: currentAccounts });

    //TODO implement deletion
  }

  handleUpdate = activity => {
    //TODO implement activity update
  }

  handleApply = activity => {
    //TODO implement appliance for activity
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
        <div className="row">
          <div className="col">
            <Link to="/accounts/new"
                  className="btn btn-primary btn-lg mt-3 position-relative start-100"
                  style={{ marginBottom: 20 }}>{t('newActivity')}
            </Link>
            {/*<Search onSearchChange={this.handleSearchChange}*/}
            {/*        value={this.state.searchQuery}/>*/}
            <AccountsTable accounts={accounts}
                             sortColumn={sortColumn}
                             onDelete={this.handleDelete}
                             onUpdate={this.handleUpdate}
                             onApply={this.handleApply}
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