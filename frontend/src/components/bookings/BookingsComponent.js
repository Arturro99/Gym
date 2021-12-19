import { withTranslation } from "react-i18next";
import '../../locales/i18n';
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

  handleComplete = booking => {
    const bookings = this.state.bookings;
    const index = bookings.findIndex(
        boo => boo.number === booking.number);
    bookings[index].completed = (bookings[index].completed === this.props.t('completed'))
        ? this.props.t('incomplete')
        : this.props.t('completed');
    this.setState({ bookings: bookings });
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
                           onComplete={this.handleComplete}
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

export default withTranslation()(BookingsComponent);