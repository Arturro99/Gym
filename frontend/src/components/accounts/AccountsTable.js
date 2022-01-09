import React, { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";
import { Link } from "react-router-dom";
import ConfirmModal from "../common/ConfirmModal";

class AccountsTable extends Component {

  state = {
    blockModalId: 'confirmBlock',
    account: ''
  }

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
            <button
                className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                data-bs-toggle='modal'
                data-bs-target={`#${this.state.blockModalId}`}
                onClick={() => this.setState({ account: account })}>
              {account.active === this.props.t('active')
                  ? this.props.t('deactivate') : this.props.t('activate')}
            </button>
            <Link
                className="btn btn-outline-success col-2 ms-2  d-flex justify-content-center text-center"
                to={`/accounts/${account.login}`}>{this.props.t('details')}
            </Link>
          </div>
    }
  ];

  render() {
    const {
      accounts,
      onSort,
      sortColumn,
      t,
      onActivate,
      onRefresh
    } = this.props;
    const { account, blockModalId } = this.state;

    return (
        <div className="card-header">
          <ConfirmModal title='confirm_block_title'
                        message={account.active === this.props.t('active')
                            ? 'confirm_block_message'
                            : 'confirm_unblock_message'}
                        modalId={`${blockModalId}`}
                        onConfirm={onActivate}
                        object={account}
                        objectBusinessId={account.login}/>
          <h1 className="text-center">{t('accounts')}</h1>
          <Table columns={this.columns}
                 data={accounts}
                 sortColumn={sortColumn}
                 onSort={onSort}
                 onRefresh={onRefresh}
                 t={t}/>
        </div>
    )
  }
}

export default withTranslation()(AccountsTable);