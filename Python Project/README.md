# NSW 2023 The Cost of Renting - University Project

## Overview
This project aims to analyze rental costs across Local Government Areas (LGAs) in New South Wales (NSW). The primary focus is to determine factors influencing weekly rental prices using a Multiple Linear Model. Additionally, K-Means Clustering is employed to identify groups based on the statistically significant variables from the Multiple Linear Model.

### Data Overview
The analysis utilizes a combination of datasets from the **Australian Bureau of Statistics** and the **NSW Department of Family and Community Services**. These datasets were merged into a single dataset named **Rent June 2023 Final**. The merging process involved using the **XLOOKUP function in Excel** before conducting the actual analysis.

## Insights
- Two primary clusters of LGAs were identified:
  1. **Low Distance - High Educational/Occupational Level**
  2. **High Distance - Low Educational/Occupational Level**
- The **Low Distance - High Educational/Occupational Level** group generally pays higher rent per week.
- Some LGAs categorized under **High Distance - Low Educational/Occupational Level** also exhibit high rental costs, possibly due to their proximity to the state's borders.
- Further analysis is required to confirm the relationship between distance, education/occupational levels, and rental costs.

## Methodology
- **Multiple Linear Model**: Used to determine key factors influencing rental prices.
- **K-Means Clustering**: Applied to segment LGAs into meaningful groups based on significant variables.

## Future Work
- Consideration of external factors such as infrastructure, transportation, and economic conditions.
- Validation of clustering results with alternative methods.

## Authors
- Oleg Baronov
