import React, { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";
import { Link } from "react-router-dom";

class AccountsTable extends Component {

  columns = [
    {
      path: 'login', label: 'login'
    },
    {
      path: 'firstName', label: 'firstName'
    },
    {
      path: 'lastName', label: 'lastName'
    },
    {
      path: 'active', label: 'activity'
    },
    {
      path: 'confirmed', label: 'status'
    },
    {
      key: 'utils', label: 'actions',
      content: account =>
          <div className="row justify-content-md-center">
            <button className="btn btn-outline-danger col-2 ms-2"
                    onClick={() => this.props.onActivate(
                        account)}>{account.active === this.props.t('active')
                ? this.props.t('deactivate') : this.props.t('activate')}
            </button>
            <button className="btn btn-outline-warning col-3 ms-2"
                    data-bs-toggle='modal' data-bs-target='#accessLevelModal'
                    onClick={() => this.props.onAccessLevels(
                        account)}>{this.props.t('accessLevels')}
            </button>
            <button className="btn btn-outline-info col-2 ms-2"
                    onClick={() => this.props.onUpdate(account)}>{this.props.t(
                'update')}
            </button>
            <Link className="btn btn-outline-success col-2 ms-2"
                  to={`/accounts/${account.login}`}>{this.props.t('details')}
            </Link>
          </div>
    }
  ];

  render() {
    const { accounts, onSort, sortColumn, t } = this.props;

    return (
        <div className="card-header">
          <h1 className="text-center">{t('accounts')}</h1>
          <Table columns={this.columns}
                 data={accounts}
                 sortColumn={sortColumn}
                 onSort={onSort}/>
        </div>
    )
  }
}

export default withTranslation()(AccountsTable);