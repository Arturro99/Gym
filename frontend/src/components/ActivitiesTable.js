import { Component } from "react";
import { Link } from "react-router-dom";
import Table from "./common/Table";

export default class ActivitiesTable extends Component {

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
                    onClick={() => this.props.onDelete(activity)}>Delete
            </button>
            <button className="btn btn-outline-info ms-2"
                    onClick={() => this.props.onUpdate(activity)}>Update
            </button>
            <button className="btn btn-outline-success ms-2"
                    onClick={() => this.props.onApply(activity)}>Apply
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