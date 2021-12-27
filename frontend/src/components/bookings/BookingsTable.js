import { Component } from "react";
import { withTranslation } from "react-i18next";
import Table from "../common/Table";
import { Link } from "react-router-dom";

class BookingsTable extends Component {

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
                className="btn btn-outline-danger col-3 ms-2 d-flex justify-content-center text-center"
                onClick={() => this.props.onDelete(booking)}>
              {this.props.myTable ?
                  this.props.t('cancel') :
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
                    onClick={() => this.props.onComplete(booking)}>
                  {booking.completed === this.props.t('completed') ?
                      this.props.t('completed') : this.props.t('complete')}
                </button>
            }
            <Link
                className="btn btn-outline-success col-3 ms-2 d-flex justify-content-center text-center"
                to={`/bookings/${booking.number}`}>{this.props.t(
                'details')}
            </Link>
          </div>
    }
  ];

  render() {
    const { bookings, onSort, sortColumn, t } = this.props;

    return (
        <div className="card-header">
          {this.props.myTable ?
              <h1 className="text-center">{t('myBookings')}</h1>
              : <h1 className="text-center">{t('bookings')}</h1>}
          <Table columns={this.columns}
                 data={bookings}
                 sortColumn={sortColumn}
                 onSort={onSort}/>
        </div>
    )
  }
}

export default withTranslation()(BookingsTable);