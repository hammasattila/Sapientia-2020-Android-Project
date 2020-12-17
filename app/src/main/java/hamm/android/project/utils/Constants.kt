package hamm.android.project.utils

const val IMAGE_REQUEST_CODE: Int = 1
const val SELECTION: String = "country = COALESCE(:country, country) AND state LIKE COALESCE(:state, state) AND INSTR(UPPER(COALESCE(:city, city)), UPPER(city)) AND INSTR(UPPER(postalCode), UPPER(COALESCE(:zip, postalCode))) AND INSTR(UPPER(address), UPPER(COALESCE(:address, address))) AND INSTR(UPPER(name), UPPER(COALESCE(:name, name))) AND price = COALESCE(:price, price)"
