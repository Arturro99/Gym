import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import BookingsTable from "../bookings/BookingsTable";
import { Booking } from "../../model/Booking";
import { Component } from "react";

class MyBookingsComponent extends Component {

  state = {
    bookings: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedBookings = {};

  componentDidMount() {
    const boo1 = new Booking(
        'BOO001',
        'arturro',
        'ACT001',
        'Active',
        'Completed',
        'Pending',
        'arturro'
    )
    const boo2 = new Booking(
        'BOO002',
        'arturro',
        'ACT002',
        'Inactive',
        'Incomplete',
        'Enrolled',
        'arturro'
    )
    this.setState({
      bookings: [boo1, boo2]
    })
  }

  handleCancel = booking => {
    const bookings = this.state.bookings;
    const index = bookings.findIndex(
        boo => boo.number === booking.number);
    bookings[index].active = (bookings[index].active === this.props.t('active'))
        ? this.props.t('inactive')
        : this.props.t('active');
    this.setState({ bookings: bookings });

    //TODO implement cancellation
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
                           onSort={this.handleSort}/>
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