import { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";
import { Link } from "react-router-dom";

class TrainingPlansTable extends Component {

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: '_name', label: 'name'
    },
    {
      path: 'trainingType', label: 'trainingType'
    },
    {
      path: 'personalTrainingsNumber', label: 'personalTrainingsNumber'
    },
    {
      path: 'trainer', label: 'trainer',
      content: trainingPlan =>
          <Link to={`/accounts/${trainingPlan.trainer}`}>
            {trainingPlan.trainer}
          </Link>
    },
    {
      path: 'price', label: 'price'
    },
    {
      key: 'utils', label: 'actions',
      content: trainingPlan =>
          <div className="row justify-content-md-center">
            <button
                className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                onClick={() => this.props.onDelete(trainingPlan)}>
              {this.props.myTable ?
                  this.props.t('cancel') : this.props.t('delete')}
            </button>
            {this.props.myTable ? null :
                <button
                    className="btn btn-outline-info col-3 ms-2 d-flex justify-content-center text-center"
                    onClick={() => this.props.onUpdate(
                        trainingPlan)}>{this.props.t(
                    'update')}
                </button>
            }
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/trainingPlans/${trainingPlan.number}`}>{this.props.t(
                'details')}
            </Link>
          </div>
    }
  ];

  render() {
    const { trainingPlans, onSort, sortColumn, t } = this.props;

    return (
        <div className="card-header">
          {this.props.myTable ?
              <h1 className="text-center">{t('myTrainingPlans')}</h1>
              : <h1 className="text-center">{t('trainingPlans')}</h1>}
          <Table columns={this.columns}
                 data={trainingPlans}
                 sortColumn={sortColumn}
                 onSort={onSort}/>
        </div>
    )
  }
}

export default withTranslation()(TrainingPlansTable);