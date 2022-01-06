import React, { Component } from "react";
import Table from "../common/Table";
import { withTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { getCurrentRole } from "../../services/AuthenticationService";
import config from "../../config.json";
import ConfirmModal from "../common/ConfirmModal";

class ActivitiesTable extends Component {

  state = {
    deactivateModalId: 'confirmActivityDeactivation',
    activity: ''
  }

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'title', label: 'name'
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
            {getCurrentRole() === config.TRAINER ?
                <button
                    className={activity.active === this.props.t('inactive') ?
                        "btn btn-outline-dark col-4 ms-2 d-flex justify-content-center text-center"
                        :
                        "btn btn-outline-danger col-4 ms-2 d-flex justify-content-center text-center"}
                    data-bs-toggle='modal'
                    data-bs-target={`#${this.state.deactivateModalId}`}
                    disabled={activity.active === this.props.t('inactive')}
                    onClick={() => this.setState({ activity: activity })}>
                  {this.props.t('deactivate')}
                </button> : ''
            }
            {getCurrentRole() === config.CLIENT ?
                <button
                    className={activity.active === this.props.t('inactive') ?
                        "btn btn-outline-dark col-3 ms-2 d-flex justify-content-center text-center"
                        :
                        "btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"}
                    disabled={activity.active === this.props.t('inactive')}
                    onClick={() => this.props.onApply(activity)}>{this.props.t(
                    'apply')}
                </button> : ''
            }
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/activities/${activity.number}`}>{this.props.t(
                'details')}
            </Link>
          </div>
    }
  ];

  render() {
    const {
      activities,
      onSort,
      sortColumn,
      t,
      onDelete,
      onRefresh
    } = this.props;
    const { activity, deactivateModalId } = this.state;

    return (
        <div className="card-header">
          <ConfirmModal title='confirm_activity_deactivation_title'
                        message='confirm_activity_deactivation_message'
                        modalId={`${deactivateModalId}`}
                        onConfirm={onDelete}
                        object={activity}
                        objectBusinessId={activity.number}/>
          <h1 className="text-center">{t('activities')}</h1>
          <Table columns={this.columns}
                 data={activities}
                 sortColumn={sortColumn}
                 onSort={onSort}
                 onRefresh={onRefresh}
                 t={t}/>
        </div>
    )
  }
}

export default withTranslation()(ActivitiesTable);