import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import BookingsTable from "../bookings/BookingsTable";
import { Component } from "react";
import {
  cancelBooking,
  completeBooking,
  createBooking,
  getBookings
} from "../../services/BookingService";

class BookingsComponent extends Component {

  state = {
    bookings: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedBookings = {};

  async componentDidMount() {
    await this.resetBookings();
  }

  handleCancel = async booking => {
    const { t } = this.props
    if (booking.active === t('active')) {
      await cancelBooking(booking.number, t)
      .catch(() => this.resetBookings())
      .then(() => this.resetBookings());
    } else {
      await createBooking(booking.activity, booking.account, t)
      .then(() => this.resetBookings());
    }
  }

  resetBookings = async () => {
    const { t } = this.props;
    const bookings = await getBookings();
    bookings.forEach(booking => {
      booking.active = booking.active ? t('active') : t('inactive');
      booking.completed = booking.completed ? t('completed') : t('incomplete');
      booking.pending = booking.pending ? t('pending') : t('non_pending');
    })
    this.setState({
      bookings: bookings
    });
  }

  handleComplete = async booking => {
    const { t } = this.props;
    await completeBooking(booking.number, t)
    .catch(() => this.resetBookings())
    .then(() => this.resetBookings());
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
            <BookingsTable bookings={bookings}
                           sortColumn={sortColumn}
                           onDelete={this.handleCancel}
                           onComplete={this.handleComplete}
                           onApply={this.handleApply}
                           onSort={this.handleSort}
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

export default withTranslation()(BookingsComponent);