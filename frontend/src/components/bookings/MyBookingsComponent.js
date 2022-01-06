import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import BookingsTable from "../bookings/BookingsTable";
import { Component } from "react";
import {
  cancelOwnBooking,
  createBooking,
  getOwnBookings
} from "../../services/BookingService";

class MyBookingsComponent extends Component {

  state = {
    bookings: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedBookings = {};

  async componentDidMount() {
    this.resetBookings();
  }

  handleCancel = async booking => {
    const { t } = this.props;
    if (booking.active === t('active')) {
      await cancelOwnBooking(booking.number, t)
      .catch(() => this.resetBookings())
      .then(() => this.resetBookings());
    } else {
      await createBooking(booking.activity, booking.account, t)
      .then(() => this.resetBookings());
    }
  }

  resetBookings = async () => {
    const { t } = this.props;
    const bookings = await getOwnBookings();
    bookings.forEach(booking => {
      booking.active = booking.active ? t('active') : t('inactive');
      booking.completed = booking.completed ? t('completed') : t('incomplete');
      booking.pending = booking.pending ? t('pending') : t('non_pending');
    })
    this.setState({
      bookings: bookings
    });
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const { length: count } = this.state.bookings;
    const {
      pageSize,
      currentPage,
      sortColumn,
      bookings
    } = this.state;
    const { t } = this.props;

    if (count === 0) {
      return <h1>{t('noBookings')}</h1>;
    }

    return (
        <div className="row mt-5">
          <div className="col">
            {/*<Search onSearchChange={this.handleSearchChange}*/}
            {/*        value={this.state.searchQuery}/>*/}
            <BookingsTable myTable={true}
                           bookings={bookings}
                           sortColumn={sortColumn}
                           onDelete={this.handleCancel}
                           onUpdate={this.handleUpdate}
                           onApply={this.handleApply}
                           onSort={this.handleSort}
                           own={true}
                           onRefresh={this.resetBookings}/>
            {/*<Pagination*/}
            {/*    itemsCount={totalCount}*/}
            {/*    pageSize={pageSize}*/}
            {/*    onPageChange={this.handlePageChange}*/}
            {/*    currentPage={currentPage}/>*/}
          </div>
        </div>
    );
  }
}

export default withTranslation()(MyBookingsComponent);