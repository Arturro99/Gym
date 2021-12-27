import { getBooking, updateBooking } from "../../services/BookingService";
import { withTranslation } from "react-i18next";
import { Component } from "react";

class UpdateBookingModal extends Component {

  state = {
    data: {
      active: '',
      completed: ''
    },
    modalId: 'updateBookingModal',
    name: 'updateBooking',
    errors: []
  }

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('show.bs.modal', () => {
      this.resetData();
    })
  }

  continueSubmitting = async () => {
    const {
      active,
      completed,
      number
    } = this.props.booking;
    const { t } = this.props;
    let newActive, newCompleted = null;
    if (active !== this.state.data.active) {
      newActive = this.state.data.active;
    }
    if (completed !== this.state.data.completed) {
      newCompleted = this.state.data.completed;
    }

    await updateBooking({
      number: number,
      active: newActive,
      completed: newCompleted
    }, t)
  }

  async resetData() {
    let currentData = { ...this.state.data };
    const booking = await getBooking(this.props.booking.number);
    currentData.active = booking.active;
    currentData.completed = booking.completed;
    this.setState({ data: currentData });
  }

  handleActiveChange = (e) => {
    let currentData = { ...this.state.data };
    currentData.active = e.currentTarget.value === 'active';
    this.setState({ data: currentData })
  }

  handleCompleteChange = (e) => {
    let currentData = { ...this.state.data };
    currentData.completed = e.currentTarget.value === 'completed';
    this.setState({ data: currentData })
  }

  render() {
    const { modalId, name } = this.state;
    const { t } = this.props;
    return (
        <div className="modal fade" id={modalId} tabIndex="-1"
             role="dialog" aria-labelledby="exampleModalCenterTitle"
             aria-hidden="true">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content bg-secondary">
              <div className="modal-header text-light">
                <h1 className="modal-title position-relative start-50 translate-middle-x">
                  {t(name)}</h1>
                <button type="button" className="btn-close btn-light"
                        data-bs-dismiss="modal" aria-label="Close"/>
              </div>
              <form className="modal-body ms-4" id="form">
                <div className="form-check">
                  <input className="form-check-input" type="radio"
                         value="active"
                         name="radioActive" id="active"
                         checked={this.state.data.active}
                         onChange={this.handleActiveChange}/>
                  <label className="form-check-label"
                         htmlFor="flexRadioDefault1">
                    {t('active')}
                  </label>
                </div>
                <div className="form-check">
                  <input className="form-check-input" type="radio"
                         value="inactive"
                         name="radioActive" id="inactive"
                         checked={!this.state.data.active}
                         onChange={this.handleActiveChange}/>
                  <label className="form-check-label"
                         htmlFor="flexRadioDefault1">
                    {t('inactive')}
                  </label>
                </div>
              </form>
              <form className="modal-body ms-4">
                <div className="form-check">
                  <input className="form-check-input" type="radio"
                         value="completed"
                         name="radioCompleted" id="completed"
                         checked={this.state.data.completed}
                         onChange={this.handleCompleteChange}/>
                  <label className="form-check-label"
                         htmlFor="flexRadioDefault1">
                    {t('completed')}
                  </label>
                </div>
                <div className="form-check">
                  <input className="form-check-input" type="radio"
                         value="incomplete"
                         name="radioCompleted" id="incomplete"
                         checked={!this.state.data.completed}
                         onChange={this.handleCompleteChange}/>
                  <label className="form-check-label"
                         htmlFor="flexRadioDefault1">
                    {t('incomplete')}
                  </label>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-primary"
                          data-bs-toggle='modal'
                          data-bs-target={`#${modalId}`}
                          onClick={this.continueSubmitting}>
                    {t('save')}</button>
                  <button type="button" className="btn btn-secondary"
                          data-bs-toggle='modal'
                          data-bs-target={`#${modalId}`}>
                    {t('cancel')}</button>
                </div>
              </form>
            </div>
          </div>
        </div>
    )
  }
}

export default withTranslation()(UpdateBookingModal);