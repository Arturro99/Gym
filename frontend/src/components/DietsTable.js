import { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "./common/Table";

class DietsTable extends Component {

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'name', label: 'name'
    },
    {
      path: 'calories', label: 'calories'
    },
    {
      path: 'mealsNumber', label: 'mealsNumber'
    },
    {
      path: 'price', label: 'price'
    },
    {
      key: 'utils', label: 'actions',
      content: activity =>
          <div>
            <button className="btn btn-outline-danger"
                    onClick={() => this.props.onDelete(activity)}>{this.props.t('delete')}
            </button>
            <button className="btn btn-outline-info ms-2"
                    onClick={() => this.props.onUpdate(activity)}>{this.props.t('update')}
            </button>
            <button className="btn btn-outline-success ms-2"
                    onClick={() => this.props.onApply(activity)}>{this.props.t('apply')}
            </button>
          </div>
    }
  ];

  render() {
    const { diets, onSort, sortColumn } = this.props;

    return (
        <Table columns={this.columns}
               data={diets}
               sortColumn={sortColumn}
               onSort={onSort}/>
    )
  }
}

export default withTranslation()(DietsTable);