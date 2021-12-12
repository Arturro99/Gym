import { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";

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
      path: 'active', label: 'active'
    },
    {
      path: 'confirmed', label: 'confirmed'
    },
    {
      key: 'utils', label: 'actions',
      content: account =>
          <div>
            <button className="btn btn-outline-danger"
                    onClick={() => this.props.onDelete(account)}>{this.props.t(
                'activate')}
            </button>
            <button className="btn btn-outline-info ms-2"
                    onClick={() => this.props.onUpdate(account)}>{this.props.t(
                'update')}
            </button>
          </div>
    }
  ];

  render() {
    const { accounts, onSort, sortColumn } = this.props;

    return (
        <Table columns={this.columns}
               data={accounts}
               sortColumn={sortColumn}
               onSort={onSort}/>
    )
  }
}

export default withTranslation()(AccountsTable);