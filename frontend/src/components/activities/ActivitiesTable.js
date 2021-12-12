import { Component } from "react";
import { Link } from "react-router-dom";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";

class ActivitiesTable extends Component {

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'name', label: 'name'
    },
    {
      path: 'startDate', label: 'startDate'
    },
    {
      path: 'duration', label: 'duration'
    },
    {
      path: 'trainer', label: 'trainer',
      // content: activity =>
      //     <Link to={'/accounts'}/>
    },
    {
      path: 'active', label: 'active'
    },
    {
      path: 'capacity', label: 'capacity'
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
    const { activities, onSort, sortColumn } = this.props;

    return (
        <Table columns={this.columns}
               data={activities}
               sortColumn={sortColumn}
               onSort={onSort}/>
    )
  }
}

export default withTranslation()(ActivitiesTable);