package ru.nidecker.nbanews.service.newsParsing;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.entity.NewsSource;
import ru.nidecker.nbanews.repository.NewsSourceLogoRepository;
import ru.nidecker.nbanews.service.NewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsParser {

    private final NewsService newsService;
    private final NewsSourceLogoRepository newsSourceLogoRepository;

    @Scheduled(fixedRate = 10000, timeUnit = TimeUnit.SECONDS, initialDelay = 30)
    @SneakyThrows
    public void parse() {
        newsSourceLogoRepository.save(new NewsSource("espn", "iVBORw0KGgoAAAANSUhEUgAABAAAAAD9CAYAAAAiXgVXAAAABGdBTUEAALGPC/xhBQAAACBjSFJN\n" +
                "AAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABmJLR0QA/wD/AP+gvaeTAAA5\n" +
                "a0lEQVR42u3dfZRl11nf+d+zz62XfpVkqaXqeum6ra5q2SokkKtaTaPg1RDAvJkQVuRxWAmBkOUw\n" +
                "YcIkYRKYkJlZk6xAmGXCBBzDMEkgYQWWLQzO2IBjiN3CyLK6uyTRdlvq7uru291V1W+S9dJvVXXv\n" +
                "3Xv+aAUD1su9fZ57z7m3vp+1sshioXOrn3P23s9+zj57mwq2ND33rSml2wWg60xhZWzh0MeJxJ93\n" +
                "dGZm8LbVzXeH0NisZtrUDFYJFrZYjCFKtxEhAOg/wUIzmV6NMTWC9Eol2CtrA5WXx48++bJJkQi9\n" +
                "Th6/a89Esvj1RAL9myvbmoJdS1IKzebLzYrqQ6vZpW21Q5d6tV+wEhQAliSN8ngBxcx1x04c/pr1\n" +
                "9o8+MTU1tFG335+CHjClXZKNWdJIUhqXbETSPTwaAIDXNCVdlNl5pXheyWqydNxiONGweGJiYf6U\n" +
                "SWldFgB2z/2okj7II4L12C+YdClJF01aTkmnkqWjWQrPpRCPjh6ff6Gsf3il6CRc0gjPD1AMk2r9\n" +
                "/m98cWrv1htWf5dkXxcsPJhS+hpJ05Iqll6Lgv575mY8FACAvyiTNKqURiV7bagwpZCUybQ8PXdl\n" +
                "SfaslJ61ZAdT0uNjJw+dWxeRSZrk8cB67ReStF3S9iR93c2uwRQtScm0ND13WaZ5kz6bTI+f35wO\n" +
                "zs3P19d9AWA4bZ2QKfD8AAWN25bO9Nu/6dz4vg1hY+ORFOM3m4VvWlFzzhQqkpRS4qYDALxtkdI3\n" +
                "SvrGZEkyaWl6tibZZ1LS7w1lQ/9127EnrvTjP9zMqoytwOvapqRvT9K3K0nbX7XrS9Nzn1NKf5hl\n" +
                "2UdGjh08XVi7LTIqS7v3fItS+gOeD6AwPzF24vD/1ev/iDM7HrijMjj0fTK9T9I3Shri1gIASmJN\n" +
                "0oEk+82hMPjRfioGLE/PPZWkh7nFQNuT8IPR0ocVKx8ZX3hqcf0UAKbmflimf8cjABTV+9j7xo4f\n" +
                "+nAv/umXZmY2N9aGvyfJ3ifp3ZIGuaEAgJK7LqXfUbBfGjt2+Ile/8csTc9dEPvmAHlESZ8Jpp8b\n" +
                "OX74k93YT6TQAsDy9J5/npT+N+47UIyU4r7xhac/30t/8/mpPTMxpH+opL8uaSN3EQDQo55Jpv97\n" +
                "bHTzb9iBA41e++PPje/bEDbUr4kNdACfiXnSF2JIP/fKwMpvzhw9utap3yn0+/toqcqtBooz2Mhq\n" +
                "vfB3JsmWpue+dWl6zyejpS8o6YeZ/AMAetxDlvQfl5euPre4e+4Hkh7NeumPzzauTTL5BxzzXdMD\n" +
                "luzXbl8bPrU0tefvpf37O7JfX6EFAGPnUKBIK9tqhy6WfeK/PD37/een5v5E0qek9G6SDQBAn5my\n" +
                "pP+4PH366cVds9/UM391TOTxQGdmyWOy9G+Xl64eWd6159v7qgAgqcoNBgqbXZ8t87nFy9N73rU8\n" +
                "NXcwyf5zMj3ADQMA9LkHLdinl3bPfvjCrgfvLv1fGzIKAEBnvSOF9PtL03v+8/mph7b1fAHgtSUN\n" +
                "o9xXoCCmUh4BuPj2h3cvTc39TlJ6XKY5bhQAYF1J9t5mGPzi4tTcXyvznxljrHKzgK50Ct8frfLF\n" +
                "87vnvqOnCwAXLqyMS6pwQ4HCEoxSFQBOTE0NLe3e8zOhGb8o0/dygwAA69g2Mz22OD33yyempkp5\n" +
                "tK1ZYAUA0L3E/e6Y9LvLU3s+kHdvgOI+AWg06TSAQuf/qVaWv+Xcfe98cKPdflAp/WSSBrg7AABI\n" +
                "Jv3djbr9jxen9o6XL5FgDwCg211CsvTjy0tXf/d09etu77kCQDOwbAgoUijBCoCkR7Ol6bl/EmI4\n" +
                "KOlB7goAAF9VBZgzaz557u2zD5Ts76IAABTj2wYHKp+71cJgYQUAiywbAoqtADQLLQBc3Ln3nuXp\n" +
                "05+W9LOShrghAAC8ofGsaZ9dnHp4Xxn+mKMzM4NiLy+gSO8wa3z27NTsrp4pAMhSlfsGFCc1s1pR\n" +
                "v31h19zXNCqNz0t6F3cCAIAWxm3pNrP4ycWpd3590X/LltXhCRV/mhiwzlk1MzuwvPOhtl6sF9dw\n" +
                "k7ECACiqu5Dqoyery0X89uL0O7+7GfQ5yarcCQAA2rLVLPz++ak9M0X+ESHj+3+gJMZTJftkO8cE\n" +
                "FvcJgNFxAEVJ0qLpsWbXJ/+7Z/+RKXxM0hbuAgAAt+T2qPS7l6p7RgrL42NGHg+Ux9ujZb91eHa2\n" +
                "pY20CykAJCkkaYJ7BRQ0cEtd//5/cXr2n1myn5OUcQcAAMg1kE/WB9Jvt5rwd6ACUOUmAKXyru2v\n" +
                "2M+XtgCwNLV3VNIg9wkoRlJ3jwBcnJr7hyb7F0QeAAA3+7a/qp8uZP6f2MwbKB3Tjy5O7XlvKQsA\n" +
                "ljXpNIBiO4iz3fqp5ek9/7OZ/jVBBwDAfUD/8aXpuW/t/u/yKS9Qyh7B0ofO3vfQaPkKAE02/wIK\n" +
                "lUKtK5P/3Xven5R+noADANCZtFpKv3JpZmZzV9MIcZoXUFJ3Zin7UOkKAApUDYFi5//Nju8BsDy9\n" +
                "511K6YM3kxMAANChGkB1bW1D1z6zS3o0M9kYcQfKmujrr5zbPfedpSoAxCQKAECBYtM6WgBYnNo7\n" +
                "npQ+kqQBog0AQGcF6UeX7pu7rxu/df7eU2OM70DJ+4Skf/NGm4QW8wmAVOW2AMXN/68MrZzr1MVP\n" +
                "TE0NBWv+lqR7CDUAAJ2XpAEl/WxXfqzCSl6gB0yNXgk/VJoCgMQKAKDANOH8zNGja526+ga7/ReS\n" +
                "tJc4AwDQ1SrA9yxNzT3U8d+JoUqwgR7oElL6qRNTU0OFFwDSze+Bd3BLgKJ6A6t16tLLU+98t0nv\n" +
                "J8gAAHSdJdNPdXz+b6wAAHrEjo12x/cXXgC4XN1zj6QN3A+gqPSgM9//L4/ObowWPkSAAQAoaIiX\n" +
                "/spbHQGWP43gNC+gd6S/X3gBYHUgUjUEiuwGUuzMBoAbwz836V4iDABAYSqVZviBziYSfMoL9JCH\n" +
                "lnbt+YZCCwDB+G4IKFIIoeZ9zcXdD3+tLP0Y0QUAoFhJ9kOpo0fw8gkA0FN9Qkg/UGgBQIlOAyhU\n" +
                "bLqvADDFD3EkEAAAJWDafX737CMdSeMlk2yCIAO91CXor/3ZIwG7XwBg4xCgWMF3D4DlqXe+W0nf\n" +
                "QGABACiHFNWRzwDOvGN2RNIwEQZ6yp1jV+xbCisAWGLjEKDInEBXddb1ghb+GWEFAKBEzN7dictW\n" +
                "6hl5PNCDYtJ3FFYASGLjEKDAjODy6PL8da+rnd899x2S/hJxBQCgVHac2/XOKfcsIrCZN9Cjvq2w\n" +
                "AoAoAACFSUquy/9T0v9BVAEAKB8LX1ny6ybyKS/Qo+5b3vnQZNcLAMu7Z++StJn4A8UIZjWva53b\n" +
                "PfdwkvYSVQAAyjjmh2/2vqYZn/ICvSoNZPu6XgBQk6ohUGjDT9FtBUCW7AeJKAAAZR3z037va8bE\n" +
                "Sl6gV1nUnu4XAEJGpwEU2vJ9TgA4Xd0/nEzvI6AAAJTWtgu7HrzbN43gZR7Qq5Lp4a4XAKLFKqEH\n" +
                "Cmz4KdY8rjM4ePV7ldIdRBQAgPKKYejtvlc0CgBArzKb6XoBwFKg0wCKLABkPisAxPJ/AAB6IOGX\n" +
                "WwHgtb28NhFUoFcnAumO5d2zd3X3EwCWDQGFWlvT2bzXuHzfI1tM6ZuJJgAAJRfTfX55fFYloECP\n" +
                "1wCaYXd3CwBsHAIUx+ylXafmX8l7mbXm2v4kDRBQAADKPvbLrwAQI3k80OtdQojV0OXfrBJ2oCAp\n" +
                "+Sz/t/StBBMAgB4Y+s1G3Ob/lsjjgR4XTSNdKwC8OLV3q6TbCTtQVBagmtOVKAAAANADLKXb/a7F\n" +
                "BoBA7/cJdk/XCgDXs3qVkANFtnjL/f3/4tTecUlvJ5gAAJRfkjxP7KEAAPT6dEBdLACEZqAAABSZ\n" +
                "BKRUy91phPgIkQQAoGfcltxO/WIzb6DXRaUtXSsAGN8NAYUK5nEEYLyfSAIA0DOyU/fObnHJ5WXk\n" +
                "8kCvzwek4e5tApjCDkIOFCg2cxcALBkFAAAAesiWGDbkvcbJe2dvS9JtRBPoeRu7VgBg51CgYFn+\n" +
                "FQBJiQIAAAA9pDEYG3mvMVTJyOOBPpCkoS5+AsCyIaBAV0ePz7+Q5wKHZ2cHTDZNKAEA6B2ra83c\n" +
                "BQCLkTwe6A/NLn4CkPgEAChO7rf/Y69UppM0QCgBAOgdmzbVcxcAZOTxQJ+IXSkAXHjwwU2SthFv\n" +
                "oBjmUABoWv1eIgkAQG+5dm1b/hUAiZW8QF9I1uhKASBd38CxIUCxJYD8GwBaoB0DANBjqlXlLgBE\n" +
                "EzkA0A/zf6XVrhQAGqFZJdxAoY29lv8ibOQJAECPedkOHMi/AoC9vIC+YKaXu1IAsJioGgLFtnZW\n" +
                "AAAAsP5cdLlKIpcH+mJKIOtSASAwcQCKlGIzdwEgsgIAAIBeS/cv5b3Ca3t53UUsgd4XU+xOAYCq\n" +
                "IVCsxoDDCgCJHYABAOghSSl3AYC9vID+YWYvdekYQL4bAgq0Ovnc/IU8Fzg3vm+DpLsJJQAAvZTs\n" +
                "p9yfALCXF9BHfYLSUujSD1E5BAqTzpqU8lwh27g2KcmIJQAAPZQBJF3Iew1LiRWAQJ9oJlvueAHg\n" +
                "dHX/cJJGCDdQFKvl7y1ClTgCANBbQsqfA5iRAwB9I2adXwEwMHx9h3hzCBQn5f/+PwZW8QAA0HsV\n" +
                "gJi7AMBeXkD/GNh8o/MrAEKD74aAYuf/0eEIQPbxAACg53KAZpa/AMBeXkC/uDxy5Mi1zu8BwBGA\n" +
                "QKFCCvkHf6r/AAD0FJPqoyery/mvQw4A9IMk1SSp8wUAJg5AwRWA5hmHq1QJJAAAPZXsnzE91sxz\n" +
                "jRNTU0Ps5QX0B7N0uisFgKTExAEoUsM8CgAU8gAA6C21vBcYjneylxfQL17bFLQLxwAaEwegwOn/\n" +
                "9h1bci3/OzozMyiq/wAA9GSyn0fI2MsL6JsuIaVuFQB4cwgU2NQX7cCBRp4rbL2xebJLfQUAAHCb\n" +
                "/3tsAsynvEC/yEIXVgAcnp0dkDRKuIFimByq/5XI4A8AQI/x2QSYzbyBfpFS7HwBYOJKmJCUEW6g\n" +
                "sBJA/uq/YpU4AgDQaxWAmLsAEI29vIB+mf/r2s15QUcLAPUmbw6BQlu6w/I/qv8AAPSeGCu5CwCW\n" +
                "2MsL6Aumi6PL89c7XgCwEKpEGyhy9M+/AiDy/R8AAL1mbWzhqeX8lyEHAPrCn9kUtNLR30nN50z2\n" +
                "k0QcKEZTejz3RaJ9QopHiSYAAL0iXDEp5r+O/aJS5HNeoMeZ2QmiAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAGD9MO8LLk7P/ZSkHYQWKKJBp1fHTsz/4zzXuHzfI1tW4+oH\n" +
                "iKbD/bDUkMIVIlHaAfCVFGM02aqCXVfSC7J4KZldXqmnC7tOzb9ClG5a2r3nW1JKjxIJoMSS/mB8\n" +
                "4fBv5bnE8u7ZvxST/U2C6TTOJPuTsYVDHyISLY41U3v+XrL0tUTCq0tI/2HixPxTf/F/X/H+oSD9\n" +
                "aJK2E3KgkKHmmbxXWLGVapC9n1h69LwmKRGH0g6Mksxu/s+UvnLPkjScmZam51YlLSTpmFI6pqBj\n" +
                "Kel53Rg8MrH45I31Faz0bSbRLwBlbqYhHct7jZi0n7bumJVZ+mWi0Fa8/pakh4mEV0DDR17vf+1a\n" +
                "ADhd3T+cdHWEaAOFDf+13H1FkxU8wGuGJM2YNCO7WRgwSdpQbyxNzx5Psj+W6YlQbz4+evqZM/2d\n" +
                "RKTJmwUtAGUVUsifA5hVqVt7pmXpDEFoI1yyKi9O/MQYax0vAAwOXZ1UFBkCUFzHecbhIjtoxcCb\n" +
                "qkh2v0n3K+n9qZJpaXr2S7LwcVP8xOjx+T/uv/m/VUnJgLJn+02PHKBKIB3zsmA1otCac+P7Nkj1\n" +
                "bUTCr0e4MrRyruMFAGumyWTMHICihCSPSvMEkQTaHgHvV0r3J9lPLE3PnpDsd0zx10ZPPP1cXySx\n" +
                "0iT3GCh5tj9QcZhsph3iLYBf3xkDKwBalG1cm0xMJD2fvvMzR4+uve58wXf2EaoEGyi0ApB78Dfj\n" +
                "EwAgZyualvRPksKXlqbm/nhxavbRtH9/pVf/NTffyuhu7itQalfGnz/4Yq7pghQkIwdw1Bho1ohC\n" +
                "i5rMI33n/2+8+sS3AJASnQZQZFuPDp8AKNCOAbdagB4xs48sL109vrh79seXR2c39to/IWyq7xCv\n" +
                "BIFyj//KvwLw3H0Pjejm3ifwsTr53PxFwtCaGBIrzVzzjzeeE7gWAJJSlWgDBXaeQwMOBYDIJwCA\n" +
                "v52W7ANpk44vTu/5weRdgO9kDtGMjO1AyQWHAkBoDjABc5XOmhSJQ6vzVWOs8Xz6UuxOAUCJGwcU\n" +
                "6MrE0Se/nL8HDrcRSqBjKc6YKf3q8vTsFxanZ7+rF/7iaCzLBMqf7Ot0/gkYxT7n/r5GDNp6iClA\n" +
                "OQrhjU8F8S0AGJsEAYUNM0k+A01KG4km0PEWe7/JPrE0PfvbF3fuvafcSRn7ggDl71J0Nn8ewQTM\n" +
                "t+80NgBkHlmcNzkVxK0AcHRmZlDSdqINFDXO5D9rNunRTHz/B3Qz4/mrjUrzi8vTe76vtH8hyzKB\n" +
                "8ucAKf8mwJG27pyXRQoA7SWhPH+e4cy6sAJgy+rwhKSMcAOFjTS5B/8X7lvm7T/QfXclpY8uTs/9\n" +
                "P68V00tWAWB/H6DssjdZ7ttyU+cNrKuQ8t+T9eK1sW+ESPjNCuK1gbMdLwCEjGVDQLFJev6lZs3G\n" +
                "lQ0EEiioCUvvv31twx+cm9n3tnKlEYzvQOn7j+Zq/rfNiQKAbwWgyQqAFm1dGdqhHtoctwdcmlh8\n" +
                "8kbHCwDG2Y1AwTl6/uV/qgxvIpJAod6VrdUPnrt373QZ/pibb2WMz/uAcrs+cvLIJYfrsN+HZ17W\n" +
                "zGpEoTUhy5hHOjK9+b5gfpUWzm4ECpUFhxUAioNEEig4aZR2hazxmTIUAV77vI+3MkC55R7/L+x6\n" +
                "8G5JvATwm4DVR09Wl4lEiyLzSNc84i32BfNbAZACNw4ocrBxWP5n9XSDSAKlaNFjIWt85tyud04V\n" +
                "+VfweR/QA8m+wylAKRuirXveE2nR9FiTSLQ44rEBpXcFoDsFgCg2CQIKdOOek0cu571Ic8PgVUIJ\n" +
                "lKgIEMKnijwmkM/7gPILln8T4GaMtHXXCa3T0czrRDSKzc4FgFpXCgAmbhxQoJpJKe9FLg6vXSGU\n" +
                "QKnsbFTix86N7ytmg04+7wPKn+un/MfNmbGS1/eeJDYAbOf5YwNK5/l/7PwKgJtnh9s44QaK6jnl\n" +
                "MtDMzc/XJa0RUKBUQ/nXh+HGLxTStUSWZQKl53AEoBIreX07T6MA0B6eP0fZWxxB6VIAOL9zYVxS\n" +
                "hXADBY0zyXWpGasAgPI18r+zPDX3N7v9s5FzwYHSiynmzwFYgu0qpfyfZawXh2dnBySNEQk/lWzw\n" +
                "bMcLAKpU6DSAYkcax0pzogAAlLGZm/7t8r2zXT2mi8/7gPIbqof8nwAko617ipEVAC0auzYwJikj\n" +
                "Em5e3HbsiSudLwAkEgSg2ImB51IzO0tEgVLaosw+1LV+Zf/+Cp/3AaV3dVvt0MX8eQSrfTwNZIEC\n" +
                "QIsajUaVKDhqYVNQlwJANL4bAgoVHQsAphMEFCinJH3X4tTso934rfNnXhkTn/cBZfeFvJsAn5vZ\n" +
                "9zZJWwmlm+a5LfEcYWgx7WSzWd88oYVVwS4FABObBAGFjjSVRs2vmJAoAABlTpbMfvbE1NRQx38n\n" +
                "qzC2A2XvD6Q/yXuNsFpnAuZr+bVNldHKM8xms65CC/uCeR0DSMcBFGdt4tgzF9w64pAdJ6RAqe3c\n" +
                "ZHf8SKd/pMlbGaD0olLuAoCFwATMd0pbIwbthIt5pG88u7QCgAIAUKizJkW3fiMmCgBAySVLP9nx\n" +
                "VQDs7wOUXmZhPvdFYqSt+/bPfP/fTt5pHAHoO3Rb5wsA6eY1Jgg3UJia58VWGptOSmoQVqDMI7xG\n" +
                "Nur2v9HZpIxdwYGSz5xeGjlefTr3/J83sK5CEgWAdp6/xPPnmh6E8JbzgtwFgMXd+7ZLGiLcQFEJ\n" +
                "gO9As7N2YEVmf0JggbK3/fSPkmSdyyJ4KwOUOtFP6TOmx5r50whW+/j2zUYBoOVh5tEs8CLZVX1t\n" +
                "rfMrAILVSRCAYhOAWgcu+jkiC5Q+y7x/+b65b+jY1UUBACh5AvCHTq2dtl72vKxPLe+qjSZpgEi4\n" +
                "jduv7Kw9+3LHCwAWqRoCRQrJv9Kckv6IyAI9oGk/2JEEVgpJGifAQGnFEPW7Ttcil/fsP7PACoBW\n" +
                "55EZ+0/4jt2tFZ/ybwJogRsHFNp7+hcA6o3GH4p9AIAeaP/pvZ3YDJDP+4CyD/16fPTU/Nm817l8\n" +
                "3yNbJL2NiPrNweorG88ShhZF5pHOPUNLc4LcBYDILsFAsZqx5n3Jm8uH7EmCC5Te1k3pjm/yvmho\n" +
                "NhjbgTLPMpP9usd1VmylSjQdp1/ShZ21AytEosV5pCWeP1/dKQAY3w0BRWpsP3XvUocGsY8SXqAH\n" +
                "JgKWvsv9ooHiPlBi14dTcBmjrUlbd+2PxQaAbT1/vEj2zgdqXSkAiJ1DgSKb+qLHDsCvp1LXhyU1\n" +
                "iTFQ9gFf3+melBnFfaDEs8xfvXPhqVd92jpLsJ1vTo0YtPUAMtY4anVfsFwFgNeOH6LjAIrqN2Ud\n" +
                "G2jurh26kJI+RZSBsvcDuvfsfQ+Nel6Tz/uA0mpkWfg5v/kqS7CdJ7SsAGhvMslY4zp4t/ZZcK4C\n" +
                "wKWde++WtIFoA0X1mx2uNFv8EFEGyi80M9fjAPm8DyjtyP/hkWMHT/vNV1kB4Hp3kv++TP2bw8pk\n" +
                "2kEkHOf/A5XOFwDWBpp0GkCRTB3daXbsxNO/l6RTBBooeQFA2uecmjG+A6Ub8lUPqfLTzjNW2rqj\n" +
                "jBUALTvzjtkRScNEws3V8ecPvtjxAoBF3hAAhUqh1uFkIwaznyXQQMm7AtODbte6+Xkfb2WA8lUA\n" +
                "Prh94akvufYdrPbx7YtZAdCySj3j2fPVcvEpXwHAqBoCxc7/mx2vNL80cP3XJHGmLVDu3uAdXle6\n" +
                "uOvBbZI2ElOgVC7eaKT/0/OCy6OzG6W0jdD6CRsa5EutziNDotDsGc9uFQDE2Y1AoQZSVuv0b8wc\n" +
                "Pbpmyf4p0QZKPfSPvji1d6vHlerZIGM7ULYWnvSPd52af8X1opu14+a8AU4ujxw5co0wtChGxhrf\n" +
                "XqJLKwCSsQIAKLDrfGHg+mI3fmj7wqHfkPQkIQfKO/KvqLnL5UKR1X1AyWb/HxldOPzr7tdtBiZg\n" +
                "npL4/r+dx5oNKJ0fv9Y3Bs97DCAdB1CcpZmjR9e60klLyRR/WNIKYQdKOvhbcjkK0DiXGSiTc3Fg\n" +
                "8H/sxIVj4A2s74RWNaLQxvPHEZS+OUBq/WjwkPO3qNwAhbX07n6XP3ri6eeS0r8k8EA5BdN2p76F\n" +
                "sR0oh7WU4nsnjj755Q7lEXyD7RpPTgBoB3vJeT9+sfOfACy+/eE7JW0m3EBRPWfrS328jI1t+VdJ\n" +
                "epzgAyUc/GNwWQEgkjKgDKKkHxxfePrznZuAsdqnqAkYJIlPyT0N1UPnCwCh3qDTAIocaAqoNNuB\n" +
                "A43Bur3PpPPcAaBkqZTSbS59C8eCASUY4/W/jJ04/Jsd7jSYgLnetM4ezdxPzk89tE3SJiLhZmVb\n" +
                "7dDFjhcAFDI6DaBAIbT+rY+nu2uHLijG90i6yl0ASlUB2OhzGSYFQLFt2f7V+MLhn+/8hJW9vDzF\n" +
                "rMkKgFYfvWyAccbXGZNSxwsAMbFEECh2pCluqdnoyafno+l/kLTGjQDKklHlLwCcm9n3NklbCCZQ\n" +
                "TCs2s386dvzQ/9rpHzoxNTUkaYSQ+9nYHKgRhRZT2CYbUHoXANr5P77lAgDfDQEFZwlZsZvNTBw/\n" +
                "/HtKepQiAFASwTbkvsRqneI+UIymWXr/6PFDP9ONHxtOWyeUfzNwfGVi9NKdC0+9SiBaHWx4kew7\n" +
                "KUjdKQCwSRBQbFNfW91c+FKzsYXD/5+l9B5JL3NLgII7BcWU9xoWOBccKMAL0fQ9o8fn/13X5qsZ\n" +
                "n/IWOQFb7yyxAaBrPNv8LDjkeNBJEoDCWrou7qwdWCnDnzK6MP+pGOMeKX2JGwMUmYCGRt5LxMiy\n" +
                "TKC7w7kOhqyyZ+L44d/r6u9G8njf/lc1gtAWnj/X56+9k8FyLP2hcgMUljAklarSPHHy6YW1evMR\n" +
                "mT7J3QEKGv8tNR06F8Z2oDsT/7rJ/sX2sc2PbH/+812fPJqRxzv3v6wAaA/Pn+fzF6zznwCcvHf2\n" +
                "Nkm3E26goIZewkrzztqzL48e3/ndJvsHkq5wl4AuJ/RJjfzXYFIAdGG2+Gkpfu3oiUP/ux040Cji\n" +
                "T4jGCgBPIRVzMlMP4/nz7FKaWecLAEOVjJsGFJrp60w5/6zHmqMnDv2blLL7Zfov3Cigqy3wikMj\n" +
                "pgAAdCxL1xmZvW9s4dBfHj3x9HOF9haJtu5bAQisAGjRmR0P3CFpK5Fwsza28NRyxwsAFiOdBlBo\n" +
                "EmGlHmjGF55aHDt++HvN0l9N0he5YUBXMvqX8l+DE36ADjiWZD90/rY0PXb80IfLkUdQAHAVmxQA\n" +
                "WpQNDfPseQ790jmTYjv/TeXW8oNUTTIiDhQ1zoT2Nvsoyujx+Y8l6b8sT+35boX0k0r6Bu4e0KEk\n" +
                "IOY7jePFqb1bV1LzDiIJOE2xpc+mpF8cWzj82+0m6B39w/bvrywvXR3jFvlpDg3WiEJrgsVqSswj\n" +
                "3drzLbwUrNzaL9kk83+gOJVoPVNpNilp4dDHJX18eXrPu5L0d6X0HklbuJOAnxjyrQC4XmlMhiaD\n" +
                "O5BzzDuZkn49y8J/Gjl28HQZ/8YLF1bGb3kOgNdzZeLok18mDK3OI8PkzfoYfDqd1J0CQDRVSRGA\n" +
                "4mRD13tyqdnoiUN/JOmPzo3v25BtqH9nsvReJftuSRu5q0A+IaYLuf77RqjKSMqANq2Z9DmZfUpm\n" +
                "f7D92MF5K/vsptHkRZ7nfFZi+X8780gl5pGuD2D7G1De4icANqlEkgAU5IW7jx692sv/gInFJ29I\n" +
                "+qikj56u7h8eHLo6q2j7lNIjMu2TdA+3GWhzbFa2mK+CkCZ5KQO8RaotnYrSs1J6NjObt+G1Pxo5\n" +
                "cuRaL/0jmiFWjSXYjn1vb3yWWaJ4TYoKlF+nFGK3PgHgnGBnL8t47YIWO86U+mpTvZ21AyuSnnjt\n" +
                "/31Akpbvnd1hIexshlg12U6L2plM22X2NotpMJo2mbRFZixh7I9nenOSBohEPtdjzFcASCRlzm7I\n" +
                "bIUw9EIGrXpSumrSmsyuKaUbkhYtaVnSuShbluzcBtnROxeeerXn+9wYJlnt4/n8GCsA2nsCmUc6\n" +
                "ymKodbwAcGlmZnN9TXcRbjeN0bHN24o6BxYoo9FT82clnZX0ONHof8vTc5+RtJ9I5HJl16n5V3Kl\n" +
                "ZLIqUwLXSeXfHztx6N8TCJRv/pWqBMEznu0vwV7neP48h5pmo+3nr+1jAJurG6na+CYIS0z+Aazv\n" +
                "bpDjqByczXuByH3wFXgriLJ2uryBdQ1nirT1Fl2+75Etkt5GJNw0tk/ettT5AoDFKrH27INVIwoA\n" +
                "1u/k/9HMpHEikY85bEJlvJVxFZtNxneUNPdkBYCnLATaeotWbIVnzzeJuqUXyW0XAMyMG+coiGVD\n" +
                "ANav8/eeGuP7f5eEPtdYsjw6u1HSNiLpN/9fsVfPEQaUb76gQNHVlzVXWQHQcqzYR843oLdW/G+7\n" +
                "AKDEEsEyJW0A0NMqJANOg0m+BHQT98H5hpyfXlhYJQ4om6WpvaOSBomEmxv3nDxymTC0OF+1UCUK\n" +
                "jvFMt7aSPNzCL5EklClpA4BeFkkGnLKqM/n+c+5Dme4H0LFHM2uSx/uqmThAtfV5D/PIMow17X8C\n" +
                "kPgEwLUdhCZJAoD1O/+nqOwzlsR8Y0mTpMz5hvB5H0o6X2iSx/sGVOTxbc1XA2ON51CTUncKAOzW\n" +
                "7GsgZSQJANZvMpBIBjwMNvKNJSRl3kkZu4KjpALFPt8xjM282xETG1C6jjXh1orNbRUAzo3v2yDp\n" +
                "bsLt1w5eGLi+SBgArOPsiWQ0v9VttUMXc85YScpcn2s+AUBZJ2C8yPOdgSXaejtdoziC0vXxazY7\n" +
                "vwIg27g2KckIt5vlmaNH1wgDgHU8fDHxzB1CnXH4BpWkzFFIsUYUUM4JGMd9una/FPtadvNFcuK0\n" +
                "GT+3fNpMe58ARN7UOCNBALCO560yySaIRO6M/kz+SzAp8K0AMClAaZHLe45jKZDLtyjbsFoVL5I9\n" +
                "n75bPm2mrQJAZJdg7xtHggBg3TrzjtkRScNEIu9Qkm+yeWJqaihJ9xBIvzuiqzpLGFC+B1MmaQeR\n" +
                "8BOzOrl868Gi+OQpx+qT9lYAsEuwb0fMEYAA1rFKnTHFZ/6fb8O54XjnDt3KscB4I5dGl+evEwaU\n" +
                "7sHcufduSRuIhJvViWPPXCAMLc7/2YDSefBXdwoA7BLsKwSOCQKwfllgVZnLWCKdyvXfZ03ug+dz\n" +
                "zed9KKm1SoO27jsDO2tSJA6tziM5gtL16Uvplsea9ir+xmZNZblxANDz2FfGKYzZ6XxJGffBdWw3\n" +
                "Pu9DOQU+5fWe0tLW25v4MNa4Pn58AtCb7SALdBwA1vHYxaoyD4ONlKsAoMR9cK4A1AgCmIDR1vFV\n" +
                "qoTAT57TZlouABydmRmUbDvh9us26isb2SQIwHruBklG87uxrXboYp4LRFb3eU8KKO6jnFjt49zU\n" +
                "I229PTx/rhWALqwA2LoyxCZBnn2wdGFn7cAKkQCwbpMnMfF0UDMp5ZsTGEmZoxj4vA9lnf/zDbbr\n" +
                "/ItiX8tuvkjWCJFwTKFynDbT8oQ+ZBmdhutdo9MAsO7T0QlikDOC0un8V6EQ46kSGd9R1tyTN7C+\n" +
                "FYBbX4K93my9sXlSvEj2lOu0mdZvBJs1eXfDdBoA1q3l3bN3SdpCJHKOJClfAeDw7OyApFEi6VgA\n" +
                "yAb5vA9ltYMQOGpQ7Gt5wslpM65MyvXstVwAMGOJoO+do9MAsI41KSq7FABCvg0Ax69m45IyIunm\n" +
                "hW3HnrhCGFA2i29/+E5RdPWcgNW3n7p3iUi0Ou1hzHcd+y3fi+SWCwBsEuR84yIbhwBYxwLHUbmE\n" +
                "UdmpPP99vRlJynyzXMZ2lLOvqDfocz3zeGnR9FiTSLQaME6bca4AdGkFQOK7IU9Z4OgQAOtX5G2A\n" +
                "TxxTvhUAZmwK5pqTJT7vQ1krABl9rqO8S7DX4ZjPWOP7AHanACDObnROEtg4BMA6Hrt4G+CiXq/X\n" +
                "8t0HCjGucyzOBUdpJ2CRPN4zj2cvrzbHGj4l951HduETgLR/f0VsEuSbJGxosEkQgPWcDZAM5I6h\n" +
                "vbSz9uzL+a7B6j7XpIxzwVHaLpeiq3P/S1tvr3esEgM/eU+baakAcOHCyrikCuF2c3nkyJFrhAHA\n" +
                "+s0FSAYcYpj7CEAzVvc5VwCYFKCsFQAKALT1QnDajL+sMtT5AkCjwcYhrn2GVCMKANZ5Nkoymn8s\n" +
                "yV0AiOzv4ypmfN6HsnYYFF1dwxlp663itBl3L+Y9baalAoAFqoaeAhuHAFjPI9fU3q2SbicS+Zjy\n" +
                "rQBIejQL0gSR9LNW560gSttjkMs7GsgCbb1FjUazShRc5X72WlrWbzFMyhLhdpKkVy/u3nMvkUCr\n" +
                "rq9tWt5ZO7CS5xrndu8bG1RjiGiiaKvNuFtGHPIPJiFXAWBp6ux2kwYIpJuXd52af4UwoGxO3jt7\n" +
                "myi6emqe2xLPEYYWZdohppGeg393CgB8N+TubzdS+tuEAa0aGnj1fknP5ep/U/3xhrSLaKJwgRC4\n" +
                "hDHkWwEQQqOaEpWYMiVlQCcMDqRJRdq6o+W5+fk6YWiNRatS9HeMp+U/baa1NCxxTjBQaKKf89SI\n" +
                "JIXEUl+grzSa8USuC8TA2O46/7fTBAGlzCGatHVnFPvamrGy/4TzWJP7+WvtGEBuHFBkz3kp76kR\n" +
                "S1N7RyUNEkugb6yNT2yt5Zr/s7rPOSdjBQBKmkWQxzu3dTbzbu/5Y/8J1+cvpdzP31sWAJIUTBon\n" +
                "3EBBHafyN3TLmnS+QH85aQcONHL1C4kCgPOsgAIAyvps0tYdhcQKgPYmrBw369ucu7ACgDeHQNEN\n" +
                "3aEA0OQzHqCvmI7nvwb9guukIIQaUUAZReO4T9/+l2JfyzmsHs14keyrXq/nHmvesgAQQoMEASi2\n" +
                "ApB/oOEoT6DfsqpjDtegX3CdZTWZFKCc81Xaum/367AEe71Y3lUbTZw24+nVnbVnX+54AUAx0GkA\n" +
                "hY7c+ZeaRQZ/oL+6BUu5NgBMksm0g0j6aQ4NMilAWfOIKkFwLABkgWJfq49e1uTZ8+UyzrxlASCy\n" +
                "cQhQ7EDjUGk2MfgDfdYz5FoBcLm65x5Jw8TRzZWJo09+mTCgbC48+OAmSXcRCb/Ot76y8SxhaFHM\n" +
                "eAHViwUANgkCipUll+9KacdAP/UL9YFcewCsVXgr48kSu4KjpLPV6xsY/z3bunRhZ+3ACpFocf5v\n" +
                "kbHGl8vqk7f+BICjG4BCVbLBXJXmJJnEUl+gjxLQV+45/dTFXNcIvJVxnWRxBCDKKnAKkGtbFxsA\n" +
                "tjXWJPLPMo41oYUnvUq4gcK8uO3YE1fyXOC1pb4bCCXQLwmovpT7IpG3Ms5ZWY0goIyakU95nRs7\n" +
                "bb2tCgCnzXgKTmNNeIskg02CgGI7ztwNfXUgUv0H+qsA8IX8XQsb/Dr31bwVRDkfzUBbL1tets5G\n" +
                "LAoArhWA0PkVAGfeMTsiNgkCius2HTYADBbofIG+yj/zFwAk9vfx7asjBQCUEysAaOtFxUoyySaI\n" +
                "hGdQmzWPy7xpAaBSJ0EAihSSw2YfbOQJ9Fe/EC13ASDxVsZVFlw2awX8sZeXb1tntU/LeJHs7tro\n" +
                "8fkXOl4AsMCbQ6DYgdthsw+jAAD0k2bFvujQufB5n6fYoACAsuYR5PKOUoq09RZV6hnPnu/T51Z8\n" +
                "evNNACMTB6DYgSb/t2aW2IAF6CPL488ffDHXBXbP3iVpM6F0c2Nk4ZkXCAPK5nR1/7CS7iESfsKG\n" +
                "xlmi0GL+GdiDyjWe8tt/4s1XALBJEFComOWvNCeJdgz0j/xv/423Ms5qJiXCgLIZGL6+QzePAoaP\n" +
                "yyNHjlwjDC0ONew/4R3R7qwAiIkbBxRpre7S2FnqC/TL8J/yf/+vyFsZTympRhRQRqHRJI/3bOui\n" +
                "rbc3YLH/hO9YE7tTADC+HQaK9PKuU/Ov5LnA4tsfvlPSFkIJ9IcY0pHc12Bs951kmdgUDCV9OHkB\n" +
                "4Izl/+2MNYkVqM6DTZf2ABCVG6BAtdwNvN6oEkagf5jpqdzXYF8QXylRAEBJn03aumv/q3SaKLQ1\n" +
                "XvH8eTbn6LcB5RsWAM5PPbRN0ibCDRTV0h2WmoWMIh7QP14ePXb4uMN16Bc8u+rgtzET4Ckabd15\n" +
                "Rkuxrz2sQHE02Mg6XwBI2QCdBlBkUulwBGC0WCWSQJ/0CUlPOm02R7/geV8ix4KhpPPVRFt3FSkA\n" +
                "tIoXye5WttUOXex4ASA2mTgABVcAcg80ljjJA+ibZF72eZ/r8FbQU2OASQFKi7buOf+vUOxrfYZZ\n" +
                "YR7pOifQWc/TZt54D4DAJkFAoX1nCPkHGjb7AvqoApCezHuJk/fO3pak2wimm9XJ5+YvEAaUzeHZ\n" +
                "2QFJo0TCz9oamwC2qhnJP33Hf98TKMIb5xlsAAgUyWVZKUd5An3TJazVG4fyXmSoktEnuN4VnfF8\n" +
                "KwN4mbgSJiRlRMJrAmYv5T2ZaX2Fiw0ofcca381m3+wUAG4cUKA4NJC/sVtgAxagP0b/53bWnn05\n" +
                "d5cQI8V91yyXIwBRTvUmbb3ME7B1EC+eP89wOm9A+WYFAG4cUJwrE0ef/HKeC7w4tXerUrqDUAJ9\n" +
                "MdP8by5XMVYFOSe5NYKAUvYYIdDWXdu6aOttPYDMI10n7Mn3tJk3/gSAAgBQXL/pMNBcz+oM/kDf\n" +
                "5J7pv/pciM/7XO8Lx4KhvIkEbd21rVMAaDOPJQd1nbHHzq8AOLPjgTvYJAgocqDJv9QsNDMGf6A/\n" +
                "rFQ21A94XIhzwZ1zskQBAGVNJCgA+MaTDQDby2MZa1zj2QydLwBUBoeqhBookp3OfwmO8gT6ZOj/\n" +
                "/ZEjR6659CxszORcAeBYMJQ0ixBt3bWpe5zMtE6c2fHAHZK2Egmvtqz66MnqcscLAMYRgECx6b7D\n" +
                "CgBLgXYM9EN/kOw3HC/GpMBRjBUmBShnv8GnvL7xjBT7WsWLZOdxRjpneqzZ8QKAmDgABbd2h2Wl\n" +
                "fP8H9IPL9cbmT3hc6NLMzGZJdxJSHybVxxZ2nCcSKN/k/9HMpHEi4RjTSuATgFb7Rl4kuwodOG3m\n" +
                "dQsAUbwhAApt7Fn+pWaJDViAPkjk0y/urB1Y8bhWc3UjSZmjTryVATws76qNJmmASLi5Mv78wRcJ\n" +
                "Q6udI5+fuOYBHTiB4vU/ARCVG6BI1qw7rACgAAD0uOvB9EteF2uyL4hvP+18LBPg9mxmTdq6a1vn\n" +
                "BID25v98fuJbAejSCgCJY4KAAl3bvvDM5TwXuPDgg5sk3UUogV4e89P/O3p8/gXHC+4gqq6zAk4A\n" +
                "QElnYJwC5NoX09bb6xp5kez8/Fm3CgC8OQQKbOq5G3q6voHOF+htVwcalZ9xTco4AcA7K6sRBJRy\n" +
                "/s9qH9p6sSUAnj/Pxy8l9+fvqwoAl+97ZIuktxFuoKBuU/kHmkZg+R/Q2yO+PnDP6acu+nYu7O/j\n" +
                "eotC5K0gyplHsJm3c0CNtt4enj9HlYr/aTNfVQBoNla5aUCxI43DEYCJpb5A704tlwazoX/tPyng\n" +
                "8z7XuxSZFKC0FQDaumdbTxT7WsWLZHfNpU31pY4XABqBZUNAoQON8i/1MQu0Y6BXc3eFH9t27Ikr\n" +
                "7n0LGzO56sRbGcCptZMDOMpCoK23aMVWePZ8Lc3Nz9c7XgBg4gAUnf07vFVKVP+Bnkzbk35/9MSh\n" +
                "3/a+7unq/mEl3UOE3TRGRoYXCQNK14dIJtkEkXBMy5qrrABodWLZYB7prNaR+8TEASiXmKJDY2cD\n" +
                "FqD3skx7KcT0I5249ODQ1UlJRpDdZllLduBAg0CgbM68Y3ZE0jCRcHPjnpNHLhOGVmeWzCNdhxpT\n" +
                "R4pPr7cCgBsHFGiwkX9ZKUewAD040Ef9yOip+bOduLY16ROck7IaUUAZVepZlSi4qpmUCEOrnSNj\n" +
                "jetEvUMnULACACiXlbtPP3UpzwVOV/cPJ2mEUAI9lDNJvzK+cOgjncsiKO77JmViSTBKyUKkrbsG\n" +
                "lLbeVrh4kewc0NiRlwLhq5MQlg4DBTqTt9I8MHx9h1jqC/SSJ2+kl3+skz8QI5uCeUrB/1xmwGe+\n" +
                "QFt3jWditU9bY03i+XMda9SZDSj/XAFgeXR2o5S2EW6guAJA7sGqSfUf6CGLA3X7vumFhdWOJrEc\n" +
                "C+ac5XIEIMo6Yw0cA+zbeZ4lCG2ESxw36zrUNEIX9gDYlNgkCChQkk7nH6tYxQP0RqKkV5LsPXfX\n" +
                "Dl3ofOdCv+AazkABACWdMPAG1retJ1b7tOrc+L4NvEj2ffwaccO5jhcAOAIQKFbgCEBgvbghi+8Z\n" +
                "P3Ho2a78mol+wXOSFT1OawE60NRN5PKuBYBAW29RtmG1Kl4kezq/s3ZgpeMFgGYSy4aAQkeamLsA\n" +
                "EEn0gbK7bil97+jxpz/bjR87OjMzKGk7Yfeb/18ZWjlHGFBS5PKejT2rs9qn9WCRf/rq2LP3F1YA\n" +
                "sEQQKHb+n7/SbInqP1Bi15LZ94wuzH+qWz+4ZXV4QlJG6N0szxw9ukYYUDbnpx7aJmkTkXCzNnHs\n" +
                "mQuEocX5f4jkn75qnbrwn98DgKXDQLGdZ1bxqPZR/QdKyS5Fpb88fvzQf+vmr4aMsd0VRwCirI9m\n" +
                "NkBb93XWpEgYWhzhOALQO6DdWQEg8eYQKNDa+PEnz+e5wOHZ2QFJY4QSKJ2FlNk3TpyYf6rrOUST\n" +
                "/X18A8qmYCin2OQNrDPaejvYgNI5nJ0ba/5iAYDKDVCc3JXm8avZuFjqC5RsFNfHGqurD48/f/B4\n" +
                "Ib8fWBXkm5RxAgBKKrDax5Wx2qdNPH+ezbmDx81W/vv/58TU1JCkEcINFKaW9wKNRrNqgQ1YgZJY\n" +
                "NdlPbF849AsmpcJy2GTVVNzP919SxhGAKOt8NXEGuyeOAGzz+ZOqjDSOss6dNvOnBYDheOcOZc1A\n" +
                "tIGiRhrL39BDmBSJPlAGx5LsfWPdOubvTUSlKmVBz4ByBCBKq0oI/ARW+7TsxNTUUJLuIRKOz99w\n" +
                "vWOnzfzphD9UIlVDoND5f/4jAI2NPIHCp4cm/XK2YW12vASTf0kyNgb17aszJgUoLXIA186Ttt6q\n" +
                "4XjnDn31p+W49Yfv0siRI9c6dfXKVxKEWE3iHQFQFJdKs6WqaMdAUZ5OKfxPYwsHnyzNZFWPZud1\n" +
                "epx1QX4hjdcGzhIGlHLKIE3S1h01We3Tcg6bNatEwbMtd/bzk69UahJHNwDF9p75Bxozvv8DCvBl\n" +
                "k/2D0RM7Hx4v0eRfks7fe2osSQPcIres7OLE4pM3CATK5syOB+5I0m1Ewk1j+44ty4Sh1fyTFajO\n" +
                "Ae3o6pM/XQEQLU0aZUOgwKEmf2OPSZO8/we65qqkX2qsrv7M5NkvvCQdKt9fWEmTSvQKbjlZYldw\n" +
                "lFM2NMweQK7Soh040CAOrYaLPahcw5lidwoAlqzKjQMKSiql+vZT9y5J87feWdxc6jtBKwY67opk\n" +
                "v1pphJ++5/RTF0v9l8ZQ5asgz6SMc8FRTsFiNVHsc8zLjLbezlBjqcqLZM8HsLPPX+XPDGss3QCK\n" +
                "SiqlRdNjzTzXWN5VGxVLfYFOZoQXUtIHV5vpg7tOHX6lR5KySaMC4PkMsAIAZU0k2OzTt7HT1tuJ\n" +
                "VjJWAHg2526sADg8OzugVzVGuIGCOk7L/1bJsjhJ9R/wn0NL+nRK6VcubNXH5ubn673Vt1iVnMwz\n" +
                "KeNccJS2sdPWPdu60dbbjFiVGDhO0GPo6PNXkaTxq9l4UzEj3EBhSWX+Sl8Mk2L9FeCTS0snJfsN\n" +
                "NRr/fvT0M737JihxLJhrOANvBVFO7AHkXgGgrbfotRfJo0TCz4BlHT1tpiJJjUazaoFuAyhwoKnl\n" +
                "HvwtVWnFQK6G+CVZ+LgpfmL78fknrD/WM1a5r45JU8xqRAFlZLR139EgcgRgq3iR7O7Ldy489WrH\n" +
                "CwAK7NwIFDv/z19ptpQmZZQAgDa8YNKBmNKno/SpHQvzJ/uqX5HCsjTBbXacZG28wVtBlBWrfRwN\n" +
                "ZIG23qJ6M07yHtlVx5+9ylcmDkQbKCxR9/iu1IzBH3hjUdLxZDoUUjocLXt87PjBI9bH1e9z9z00\n" +
                "kkUNcevdXB45cuQaYUDZXJqZ2Vxf051Ewk3zhYHri4Sh1fSTk+ScZwW1Tv/CzRUAlqqiAgAUxqXS\n" +
                "nMRxX4Akk87L9LySPZ8sHY1RRzcqe7rTS+rKJjQHJmWRB8ItJ+MEAJR0trq6kT2AfJ2fOXp0jTC0\n" +
                "OObyItl5qLHurABIZhUlnSLkQCGTlbi4uZm/0mypkWS0Y/R5oqEvy/RlJb0ks4tSWrRkywrNs5my\n" +
                "pVfjS0vTCwurREqSNd9Gn+AnmA4TBZTUXUnk8W5tXXqaKLTVOQ6nlHj+/PKcL3b6N/5/9go9P5I/\n" +
                "/l8AAAAldEVYdGRhdGU6Y3JlYXRlADIwMjItMDMtMDZUMDA6MDc6NDkrMDA6MDAhBRBvAAAAJXRF\n" +
                "WHRkYXRlOm1vZGlmeQAyMDIyLTAzLTA2VDAwOjA3OjQ5KzAwOjAwUFio0wAAAABJRU5ErkJggg=="));
        Thread.sleep(1000);
        parseEspnCom();
    }

    private void parseEspnCom() throws IOException {
        NewsSource newsSource = newsService.chooseNewsSource("espn");
        Document document = Jsoup.connect("https://www.espn.com/nba/").get();

        Elements content = document.getElementsByClass("has-image");

        List<News> news = new ArrayList<>();

        for (Element element : content) {
            String title = element.getElementsByTag("h1").size() == 0 ? "" :
                    element.getElementsByTag("h1").get(0).text();

            String subhead = element.getElementsByTag("p").size() == 0 ? "" :
                    element.getElementsByTag("p").get(0).text();

            String originalSource = element.getElementsByTag("a").size() == 0 ? "" :
                    "https://www.espn.com" + element.getElementsByTag("a").get(0).attr("href");

            String contentAuthor = element.getElementsByClass("contentMeta__author").size() == 0 ?
                    "" : element.getElementsByClass("contentMeta__author").get(0).text();

            String imageURL = element.getElementsByTag("picture").size() == 0 ? "" :
                    element.getElementsByTag("picture").get(0)
                            .getElementsByTag("img").get(0).attr("data-default-src");

            news.add(mapToNewsEntity(title, subhead, originalSource, contentAuthor, imageURL, newsSource));
        }

        newsService.saveAll(news);
    }

    private News mapToNewsEntity(String title, String subhead, String originalSource, String contentAuthor, String imageURL, NewsSource siteName) {
        News news = null;
        switch (siteName.getName()) {
            case "espn" -> {
                news = newsService.prepare(title, subhead, originalSource, imageURL, siteName, contentAuthor);
            }
        }
        return news;
    }
}