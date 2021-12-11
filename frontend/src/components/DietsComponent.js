import { withTranslation } from "react-i18next";
import '../locales/i18n';
import { Link } from "react-router-dom";
import { Diet } from "../model/Diet";
import { Component } from "react";
import DietsTable from "./DietsTable";

class DietsComponent extends Component {

  state = {
    diets: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedDiets = {};

  componentDidMount() {
    const die1 = new Diet(
        'DIE001',
        'Niskoweglowodanowa',
        '1800',
        3,
        1100
    )
    const die2 = new Diet(
        'DIE002',
        'Wysokobiałkowa',
        '3200',
        4,
        1800
    )
    this.setState({
      diets: [die1, die2]
    })
  }

  handleDelete = diet => {
    const originalDiets = this.state.diets;
    const currentDiets = originalDiets.filter(
        die => die.number !== diet.number);
    this.setState({ diets: currentDiets });

    //TODO implement deletion
  }

  handleUpdate = diet => {
    //TODO implement diet update
  }

  handleApply = diet => {
    //TODO implement appliance for diet
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const { length: count } = this.state.diets;
    const {
      pageSize,
      currentPage,
      sortColumn,
      diets
    } = this.state;
    const { t } = this.props;

    if (count === 0) {
      return <h1>{t('noDiets')}</h1>;
    }

    return (
        <div className="row">
          <div className="col">
            <Link to="/diets/new"
                  className="btn btn-primary btn-lg mt-3 position-relative start-100"
                  style={{ marginBottom: 20 }}>{t('newDiet')}
            </Link>
            {/*<Search onSearchChange={this.handleSearchChange}*/}
            {/*        value={this.state.searchQuery}/>*/}
            <DietsTable diets={diets}
                        sortColumn={sortColumn}
                        onDelete={this.handleDelete}
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

export default withTranslation()(DietsComponent);