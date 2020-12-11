package hamm.android.project.utils

const val IMAGE_REQUEST_CODE: Int = 1
const val SELECTION: String = "country = COALESCE(:country, country) AND state LIKE COALESCE(:state, state) AND INSTR(COALESCE(:city, city), city) AND INSTR(postalCode, COALESCE(:zip, postalCode)) AND INSTR(address, COALESCE(:address, address)) AND INSTR(name, COALESCE(:name, name)) AND price = COALESCE(:price, price)"
