import React, { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";
import { Link } from "react-router-dom";
import ConfirmModal from "../common/ConfirmModal";

class BookingsTable extends Component {

  state = {
    deactivateModalId: 'confirmBookingDeactivation',
    completeModalId: 'confirmBookingCompletion',
    booking: ''
  }

  columns = [
    {
      path: 'number', label: 'number'
    },
    {
      path: 'account', label: 'account',
      content: booking =>
          <Link to={`/accounts/${booking.account}`}>
            {booking.account}
          </Link>
    },
    {
      path: 'activity', label: 'activity',
      content: booking =>
          <Link to={`/activities/${booking.activity}`}>
            {booking.activity}
          </Link>
    },
    {
      path: 'active', label: 'active'
    },
    {
      path: 'completed', label: 'completed',
    },
    {
      path: 'pending', label: 'state'
    },
    {
      key: 'utils', label: 'actions',
      content: booking =>
          <div className="row justify-content-md-center">
            <button
                className={booking.active === this.props.t('active')
                    ? "btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                    : "btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"}
                data-bs-toggle='modal'
                data-bs-target={`#${this.state.deactivateModalId}`}
                onClick={() => this.setState(
                    { booking: booking })}>
              {this.props.myTable ?
                  (booking.active === this.props.t('active') ? this.props.t(
                      'cancel') : this.props.t(
                      'apply')) :
                  booking.active === this.props.t('active') ?
                      this.props.t('deactivate') : this.props.t('activate')}
            </button>
            {this.props.myTable ? null :
                <button
                    className={booking.completed === this.props.t('completed') ?
                        "btn btn-outline-dark col-3 ms-2 d-flex justify-content-center text-center"
                        :
                        "btn btn-outline-warning col-3 ms-2 d-flex justify-content-center text-center"}
                    disabled={booking.completed === this.props.t('completed')}
                    data-bs-toggle='modal'
                    data-bs-target={`#${this.state.completeModalId}`}
                    onClick={() => this.setState(
                        { booking: booking })}>
                  {booking.completed === this.props.t('completed') ?
                      this.props.t('completed') : this.props.t('complete')}
                </button>
            }
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/bookings/${this.props.own ? "own/"
                    : ""}${booking.number}`}>{this.props.t(
                'details')}
            </Link>
          </div>
    }
  ];

  render() {
    const {
      bookings,
      onSort,
      own,
      sortColumn,
      t,
      onDelete,
      onComplete,
      myTable,
      onRefresh
    } = this.props;
    const { booking, deactivateModalId, completeModalId } = this.state;
    if (own) {
      this.columns = this.columns.filter(column => column.path !== 'account');
    }
    return (
        <div className="card-header">
          <ConfirmModal
              title={myTable ? (booking.active === t('active')
                  ? 'confirm_booking_cancellation_title'
                  : 'confirm_booking_activation_title')
                  : (booking.active === t('active')
                      ? 'confirm_booking_deactivation_title'
                      : 'confirm_booking_activation_title')}
              message={myTable ? (booking.active === t('active')
                  ? 'confirm_booking_cancellation_message'
                  : 'confirm_booking_activation_message')
                  : (booking.active === t('active')
                      ? 'confirm_booking_deactivation_message'
                      : 'confirm_booking_activation_message')}
              modalId={`${deactivateModalId}`}
              onConfirm={onDelete}
              object={booking}
              objectBusinessId={booking.number}/>
          <ConfirmModal
              title={'confirm_booking_completion_title'}
              message={'confirm_booking_completion_message'}
              modalId={`${completeModalId}`}
              onConfirm={onComplete}
              object={booking}
              objectBusinessId={booking.number}/>
          {this.props.myTable ?
              <h1 className="text-center">{t('myBookings')}</h1>
              : <h1 className="text-center">{t('bookings')}</h1>}
          <Table columns={this.columns}
                 data={bookings}
                 sortColumn={sortColumn}
                 onSort={onSort}
                 onRefresh={onRefresh}
                 t={t}/>
        </div>
    )
  }
}

export default withTranslation()(BookingsTable);