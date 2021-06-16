import * as yup from 'yup';

export const roomValidation = yup.object().shape({
  address: yup
    .string()
    .max(40, 'Adresas yra per ilgas, turi būti iki 40 simbolių')
    .required('Adresas yra privalomas'),
  building: yup
    .string()
    .max(40, 'Pastatas yra per ilgas, turi būti iki 40 simbolių')
    .required('Pastatas yra privalomas'),
  number: yup
    .string()
    .max(40, 'Auditorijos numeris yra per ilgas, turi būti iki 40 simbolių')
    .required('Auditorijos numeris yra privalomas'),
  type: yup
    .string()
    .max(40, 'Tipas per ilgas, turi būti iki 40 simbolių')
    .required('Tipas yra privalomas')
});

export const userValidation = yup.object().shape({
  username: yup
    .string()
    .required('Prisijungimo vardas yra privalomas')
    .max(40, 'Prisijungimo vardas yra per ilgas, turi būti iki 40 simbolių'),
  password: yup
    .string()
    .required('Slaptažodis yra privalomas')
    .matches(/[a-zA-Z]/, 'Slaptažodis turi susidaryti iš lotyniškų raidžių')
});
