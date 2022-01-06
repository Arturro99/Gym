import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { Booking } from "../../model/Booking";
import { parseFromOffsetDateTimeToLegibleFormat } from "../../services/DateParser";
import { getBooking, getOwnBooking } from "../../services/BookingService";
import config from "../../config.json";
import { getCurrentRole } from "../../services/AuthenticationService";

class BookingDetails extends Details {

  state = {
    data: {
      booking: Booking
    }
  }

  async componentDidMount() {
    await this.updateDetails();
  }

  updateDetails = async () => {
    const pathParam = this.props.match.params.number;
    const { t, own } = this.props;
    let currentState = { ...this.state };
    let fetched;
    if (own) {
      fetched = await getOwnBooking(pathParam);
    } else {
      fetched = await getBooking(pathParam);
    }
    currentState.data.booking = new Booking(
        fetched.number,
        fetched.account,
        fetched.activity,
        fetched.active ? t('active') : t('inactive'),
        fetched.completed ? t('completed') : t('incomplete'),
        fetched.pending ? t('pending') : t('non_pending'),
        fetched.modifiedBy,
        fetched.createdBy,
        parseFromOffsetDateTimeToLegibleFormat(fetched.creationDate),
        parseFromOffsetDateTimeToLegibleFormat(fetched.modificationDate)
    );
    this.setState({ currentState });
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <div className="card-header">
            <h1>{t('bookingDetails')}</h1>
            {this.renderRefreshButton(this.updateDetails, t)}
            {getCurrentRole() === config.TRAINER ?
                this.renderUpdateButton('updateBookingModal', t('update')) : ''}
          </div>
          {this.renderField('number', t('number'),
              this.state.data.booking)}
          {this.renderField('account', t('account'), this.state.data.booking, true)}
          {this.renderField('activity', t('activity'),
              this.state.data.booking, true, 'activities')}
          {this.renderField('active',
              t('active'), this.state.data.booking)}
          {this.renderField('completed', t('completed'),
              this.state.data.booking)}
          {this.renderField('pending', t('state'), this.state.data.booking)}
          {this.renderField('createdBy', t('createdBy'),
              this.state.data.booking, true)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.booking, true)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.booking)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.booking)}
        </div>
    );
  }
}

export default withTranslation()(BookingDetails);