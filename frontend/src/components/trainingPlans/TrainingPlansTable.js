import { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";

class TrainingPlansTable extends Component {

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'name', label: 'name'
    },
    {
      path: 'trainingType', label: 'trainingType'
    },
    {
      path: 'personalTrainingsNumber', label: 'personalTrainingsNumber'
    },
    {
      path: 'trainer', label: 'trainer'
    },
    {
      path: 'price', label: 'price'
    },
    {
      key: 'utils', label: 'actions',
      content: trainingPlan =>
          <div>
            <button className="btn btn-outline-danger"
                    onClick={() => this.props.onDelete(trainingPlan)}>{this.props.t(
                'delete')}
            </button>
            <button className="btn btn-outline-info ms-2"
                    onClick={() => this.props.onUpdate(trainingPlan)}>{this.props.t(
                'update')}
            </button>
            <button className="btn btn-outline-success ms-2"
                    onClick={() => this.props.onApply(trainingPlan)}>{this.props.t(
                'apply')}
            </button>
          </div>
    }
  ];

  render() {
    const { trainingPlans, onSort, sortColumn } = this.props;

    return (
        <Table columns={this.columns}
               data={trainingPlans}
               sortColumn={sortColumn}
               onSort={onSort}/>
    )
  }
}

export default withTranslation()(TrainingPlansTable);