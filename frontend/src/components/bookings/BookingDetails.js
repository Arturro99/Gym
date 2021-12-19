import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { Booking } from "../../model/Booking";

class BookingDetails extends Details {

  state = {
    data: {
      booking: Booking
    }
  }

  componentDidMount() {
    const pathParam = this.props.match.params.number;
    let currentState = { ...this.state };
    currentState.data.booking.number = pathParam;
    currentState.data.booking.account = 'Arturro';
    currentState.data.booking.activity = 'ACT001';
    currentState.data.booking.completed = 'false';
    currentState.data.booking.creationDate = '21.12.2020';
    this.setState({ currentState });

    //TODO Populate details
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <div className="card-header">
            <h1>{t('bookingDetails')}</h1>
          </div>
          {this.renderField('number', t('number'),
              this.state.data.booking)}
          {this.renderField('account', t('account'), this.state.data.booking)}
          {this.renderField('activity', t('activity'),
              this.state.data.booking)}
          {this.renderField('active',
              t('active'), this.state.data.booking)}
          {this.renderField('completed', t('completed'),
              this.state.data.booking)}
          {this.renderField('pending', t('pending'), this.state.data.booking)}
          {this.renderField('createdBy', t('createdBy'),
              this.state.data.booking)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.booking)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.booking)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.booking)}
        </div>
    );
  }
}

export default withTranslation()(BookingDetails);