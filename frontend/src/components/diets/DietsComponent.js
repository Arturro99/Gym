import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Link } from "react-router-dom";
import { Component } from "react";
import DietsTable from "./DietsTable";
import { deleteDiet, getDiets } from "../../services/DietService";

class DietsComponent extends Component {

  state = {
    diets: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedDiets = {};

  async componentDidMount() {
    const diets = await getDiets();
    this.setState({
      diets: diets
    })
  }

  handleDelete = async diet => {
    const originalDiets = this.state.diets;
    const currentDiets = originalDiets.filter(
        die => die.number !== diet.number);
    this.setState({ diets: currentDiets });

    try {
      await deleteDiet(diet.number);
    } catch (ex) {
      this.setState({ movies: originalDiets });
    }
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
        <div className="row mt-5">
          <div className="col">
            {/*<Search onSearchChange={this.handleSearchChange}*/}
            {/*        value={this.state.searchQuery}/>*/}
            <DietsTable diets={diets}
                        sortColumn={sortColumn}
                        onDelete={this.handleDelete}
                        onUpdate={this.handleUpdate}
                        onApply={this.handleApply}
                        onSort={this.handleSort}/>
            <Link to="/diets/new"
                  className="btn btn-primary btn-lg mt-3 float-end"
                  style={{ marginBottom: 20 }}>{t('newDiet')}
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

export default withTranslation()(DietsComponent);