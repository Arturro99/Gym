import { Component } from "react";
import { withTranslation } from "react-i18next";

class ConfirmModal extends Component {

  render() {
    const {
      t,
      onConfirm,
      object,
      objectBusinessId,
      message,
      modalId,
      title
    } = this.props;
    return (
        <div className="modal fade" id={modalId} tabIndex="-1"
             role="dialog" aria-labelledby="exampleModalCenterTitle"
             aria-hidden="true">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content bg-secondary">
              <div className="modal-header text-light">
                <h1 className="modal-title position-relative start-50 translate-middle-x">
                  {t(title)}</h1>
                <button type="button" className="btn-close btn-light"
                        data-bs-dismiss="modal" aria-label="Close"/>
              </div>
              <div className="modal-body ms-4 text-light modal-dialog-centered"
                   style={{whiteSpace: "pre-line"}}>
                {t(message)} {objectBusinessId}?
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary"
                        data-bs-toggle='modal'
                        data-bs-target={`#${modalId}`}
                        onClick={() => onConfirm(object)}>
                  {t('yes')}</button>
                <button type="button" className="btn btn-secondary"
                        data-bs-toggle='modal'
                        data-bs-target={`#${modalId}`}>
                  {t('no')}</button>
              </div>
            </div>
          </div>
        </div>
    );
  }
}

export default withTranslation()(ConfirmModal);