import { Component } from "react";
import Table from "../common/Table";
import { withTranslation } from "react-i18next";
import { Link } from "react-router-dom";

class ActivitiesTable extends Component {

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: '_name', label: 'name'
    },
    {
      path: 'startDate', label: 'startDate'
    },
    {
      path: 'duration', label: 'duration'
    },
    {
      path: 'trainer', label: 'trainer',
      content: activity =>
          <Link to={`/accounts/${activity.trainer}`}>
            {activity.trainer}
          </Link>
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
          <div className="row justify-content-md-center">
            <button
                className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                onClick={() => this.props.onDelete(activity)}>{this.props.t(
                'delete')}
            </button>
            <button
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                onClick={() => this.props.onApply(activity)}>{this.props.t(
                'apply')}
            </button>
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/activities/${activity.number}`}>{this.props.t(
                'details')}
            </Link>
          </div>
    }
  ];

  render() {
    const { activities, onSort, sortColumn, t } = this.props;

    return (
        <div className="card-header">
          <h1 className="text-center">{t('activities')}</h1>
          <Table columns={this.columns}
                 data={activities}
                 sortColumn={sortColumn}
                 onSort={onSort}/>
        </div>
    )
  }
}

export default withTranslation()(ActivitiesTable);