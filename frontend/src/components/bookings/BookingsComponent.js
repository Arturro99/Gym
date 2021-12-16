import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Link } from "react-router-dom";
import BookingsTable from "../bookings/BookingsTable";
import { Booking } from "../../model/Booking";
import { Component } from "react";

class BookingsComponent extends Component {

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
        'arturro2',
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

  handleDelete = booking => {
    const originalBookings = this.state.bookings;
    const currentBookings = originalBookings.filter(
        boo => boo.number !== booking.number);
    this.setState({ bookings: currentBookings });

    //TODO implement deletion
  }

  handleUpdate = booking => {
    //TODO implement booking update
  }

  handleApply = booking => {
    //TODO implement appliance for booking
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
                                onDelete={this.handleDelete}
                                onUpdate={this.handleUpdate}
                                onApply={this.handleApply}
                                onSort={this.handleSort}/>
            <Link to="/bookings/new"
                  className="btn btn-primary btn-lg mt-3 float-end"
                  style={{ marginBottom: 20 }}>{t('newBooking')}
            </Link>
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