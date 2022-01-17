import React, { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";
import { Link } from "react-router-dom";
import { getCurrentRole } from "../../services/AuthenticationService";
import config from "../../config.json";
import ConfirmModal from "../common/ConfirmModal";

class TrainingPlansTable extends Component {

  state = {
    deleteModalId: 'confirmTrainingPlanDeletion',
    trainingPlan: ''
  }

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'title', label: 'name'
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
            {!this.props.myTable && getCurrentRole() !== config.CLIENT ?
                <button
                    className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                    data-bs-toggle='modal'
                    data-bs-target={`#${this.state.deleteModalId}`}
                    onClick={() => this.setState(
                        { trainingPlan: trainingPlan })}>
                  {this.props.t('delete')}
                </button> : ''
            }
            {this.props.myTable ?
                <button
                    className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                    data-bs-toggle='modal'
                    data-bs-target={`#${this.state.deleteModalId}`}
                    onClick={() => this.setState(
                        { trainingPlan: trainingPlan })}>
                  {this.props.t('cancel')}
                </button> :
                <button
                    className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                    onClick={() => this.props.onApply(trainingPlan)}>
                  {this.props.t('apply')}
                </button>
            }
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/trainingPlans/${trainingPlan.number}`}>
              {this.props.t('details')}
            </Link>
          </div>
    }
  ];

  render() {
    const {
      trainingPlans,
      onSort,
      sortColumn,
      t,
      onDelete,
      myTable,
      onRefresh
    } = this.props;
    const { trainingPlan, deleteModalId } = this.state;

    return (
        <div className="card-header bg-light">
          <ConfirmModal
              title={myTable ? 'confirm_trainingPlan_cancellation_title'
                  : 'confirm_trainingPlan_deletion_title'}
              message={myTable ? 'confirm_trainingPlan_cancellation_message'
                  : 'confirm_trainingPlan_deletion_message'}
              modalId={`${deleteModalId}`}
              onConfirm={onDelete}
              object={trainingPlan}
              objectBusinessId={trainingPlan.number}/>
          {this.props.myTable ?
              <h1 className="text-center">{t('myTrainingPlans')}</h1>
              : <h1 className="text-center">{t('trainingPlans')}</h1>}
          <Table columns={this.columns}
                 data={trainingPlans}
                 sortColumn={sortColumn}
                 onSort={onSort}
                 onRefresh={onRefresh}
                 t={t}/>
        </div>
    )
  }
}

export default withTranslation()(TrainingPlansTable);