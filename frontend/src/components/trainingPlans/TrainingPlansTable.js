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
                    onClick={() => this.props.onDelete(
                        trainingPlan)}>{this.props.t(
                'delete')}
            </button>
            {this.props.myTable ? null :
                <button className="btn btn-outline-info ms-2"
                        onClick={() => this.props.onUpdate(
                            trainingPlan)}>{this.props.t(
                    'update')}
                </button>
            }
            {this.props.myTable ? null :
                <button className="btn btn-outline-success ms-2"
                        onClick={() => this.props.onApply(
                            trainingPlan)}>{this.props.t(
                    'apply')}
                </button>
            }
          </div>
    }
  ];

  render() {
    const { trainingPlans, onSort, sortColumn, t } = this.props;

    return (
        <div
            className="position-absolute top-25 start-50 translate-middle mt-5">
          {this.props.myTable ?
              <h1 className="text-center">{t('myTrainingPlans')}</h1>
              : null}
          <Table columns={this.columns}
                 data={trainingPlans}
                 sortColumn={sortColumn}
                 onSort={onSort}/>
        </div>
    )
  }
}

export default withTranslation()(TrainingPlansTable);